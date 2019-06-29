# Scala & Spark
In this branch we will play with Spark to analyse some data. 

Main class is SparkRunner.scala where you can run the following jobs:

## CustomerExpenditures
"CUST_EXP" "src/main/resources/data/customer-orders.csv"

## DegreesOfSeparation
"FIND_HERO"  "src/main/resources/data/Marvel-graph.txt"

## MinTemperatures
"MIN_TMP"  "src/main/resources/data/1800.csv"

## MostPopularSuperHero
"POP_HERO"  "src/main/resources/data/Marvel-graph.txt" "src/main/resources/data/Marvel-names.txt"

## WordCount
"WC" "local[*]" "src/main/resources/data/book.txt"

## EEG
Arguments:
EEG src/main/resources/data/EEG/Bart_NounVerb.csv src/main/resources/data/EEG/output/bart

Note that for the EEG case it whould be better to use Spark Structured Streaming. The data is fixed, thus a schema can be used, so that we can use SparkSQL to retrieve the data. 

