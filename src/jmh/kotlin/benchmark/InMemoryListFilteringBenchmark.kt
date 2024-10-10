package benchmark

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import kotlin.random.Random


data class FilterableObject(
    val date: LocalDate,
    val groupId: Int,
    val genericFields: List<Field>
)

data class Field(val key: String, val value: String)

@Fork(1) // runs the benchmark in a new JVM process.
@Measurement(iterations = 10) //how many iterations of the actual benchmark will be run to collect data
@BenchmarkMode(Mode.AverageTime)// Measures the average time taken per operation.
@OutputTimeUnit(TimeUnit.MILLISECONDS)// Outputs the result in milliseconds.
@State(Scope.Benchmark)// Indicates that the benchmark state is shared across all iterations.
open class InMemoryListFilteringBenchmark {

    lateinit var smallList: List<FilterableObject>
    lateinit var mediumList: List<FilterableObject>
    lateinit var largeList: List<FilterableObject>
    lateinit var veryLargeList: List<FilterableObject>
    lateinit var hugeList: List<FilterableObject>

    @Setup(Level.Invocation) // Prepares the data before each invocation (generates the lists of different sizes).
    fun setup() {
        val genericFields = mapOf(
            "status" to listOf("Active", "Inactive", "Pending", "Suspended", "Archived"),
            "priorityLevel" to listOf("Low", "Medium", "High", "Critical", "Urgent"),
            "countryCode" to listOf("US", "ES", "DE", "UK", "IT"),
            "language" to listOf("English", "Spanish", "German", "French", "Chinese")
        )
        val groups = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val dateRange = Pair(LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-12"))
        smallList = generateList(size = 100, groups = groups, dateRange = dateRange, genericFields = genericFields)
        mediumList = generateList(size = 1_000, groups = groups, dateRange = dateRange, genericFields = genericFields)
        largeList = generateList(size = 10_000, groups = groups, dateRange = dateRange, genericFields = genericFields)
        veryLargeList = generateList(size = 100_000, groups = groups, dateRange = dateRange, genericFields = genericFields)
        hugeList = generateList(size = 500_000, groups = groups, dateRange = dateRange, genericFields = genericFields)
    }

    private val simplePredicate: (FilterableObject) -> Boolean = { it.groupId == 2 }

    private val complexPredicate: (FilterableObject) -> Boolean = {
        it.groupId == 2 &&
                it.date.isAfter(LocalDate.parse("2024-02-01")) &&
                it.date.isBefore(LocalDate.parse("2024-05-01")) &&
                it.genericFields.contains(Field("priorityLevel", "Low"))
        it.genericFields.contains(Field("status", "Suspended"))
    }

    @Benchmark
    fun listWith100ItemsAndSimpleFiltering(): List<FilterableObject> {
        return smallList.filter(simplePredicate)
    }

    @Benchmark
    fun listWith1000ItemsAndSimpleFiltering(): List<FilterableObject> {
        return mediumList.filter(simplePredicate)
    }

    @Benchmark
    fun listWith10000ItemsAndSimpleFiltering(): List<FilterableObject> {
        return largeList.filter(simplePredicate)
    }

    @Benchmark
    fun listWith100000ItemsAndSimpleFiltering(): List<FilterableObject> {
        return veryLargeList.filter(simplePredicate)
    }

    @Benchmark
    fun listWith500000ItemsAndSimpleFiltering(): List<FilterableObject> {
        return hugeList.filter(simplePredicate)
    }

    @Benchmark
    fun listWith100ItemsAndComplexFiltering(): List<FilterableObject> {
        return smallList.filter(complexPredicate)
    }

    @Benchmark
    fun listWith1000ItemsAndComplexFiltering(): List<FilterableObject> {
        return mediumList.filter(complexPredicate)
    }

    @Benchmark
    fun listWith10000ItemsAndComplexFiltering(): List<FilterableObject> {
        return largeList.filter(complexPredicate)
    }

    @Benchmark
    fun listWith100000ItemsAndComplexFiltering(): List<FilterableObject> {
        return veryLargeList.filter(complexPredicate)
    }

    @Benchmark
    fun listWith500000ItemsAndComplexFiltering(): List<FilterableObject> {
        return hugeList.filter(complexPredicate)
    }


    private fun generateList(
        size: Int,
        groups: Set<Int>,
        dateRange: Pair<LocalDate, LocalDate>,
        genericFields: Map<String, List<String>>
    ): List<FilterableObject> =
        (1..size).map {
            FilterableObject(
                date = LocalDate.ofEpochDay(
                    Random.nextLong(
                        dateRange.first.toEpochDay(),
                        dateRange.second.toEpochDay() + 1
                    )
                ),
                groupId = groups.random(),
                genericFields = genericFields.entries.map { (key, values) -> Field(key, values.random()) }
            )
        }
}
