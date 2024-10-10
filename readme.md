# In-memory list filtering

Results of the benchmarks:
```shell
Benchmark                                                              Mode  Cnt   Score    Error  Units
InMemoryListFilteringBenchmark.listWith100000ItemsAndComplexFiltering  avgt   10   6.942 ±  1.678  ms/op
InMemoryListFilteringBenchmark.listWith100000ItemsAndSimpleFiltering   avgt   10   1.188 ±  0.013  ms/op
InMemoryListFilteringBenchmark.listWith10000ItemsAndComplexFiltering   avgt   10   0.549 ±  0.097  ms/op
InMemoryListFilteringBenchmark.listWith10000ItemsAndSimpleFiltering    avgt   10   0.121 ±  0.003  ms/op
InMemoryListFilteringBenchmark.listWith1000ItemsAndComplexFiltering    avgt   10   0.068 ±  0.006  ms/op
InMemoryListFilteringBenchmark.listWith1000ItemsAndSimpleFiltering     avgt   10   0.015 ±  0.001  ms/op
InMemoryListFilteringBenchmark.listWith100ItemsAndComplexFiltering     avgt   10   0.019 ±  0.001  ms/op
InMemoryListFilteringBenchmark.listWith100ItemsAndSimpleFiltering      avgt   10   0.004 ±  0.001  ms/op
InMemoryListFilteringBenchmark.listWith500000ItemsAndComplexFiltering  avgt   10  30.180 ±  3.021  ms/op
InMemoryListFilteringBenchmark.listWith500000ItemsAndSimpleFiltering   avgt   10   4.098 ±  0.183  ms/op
```