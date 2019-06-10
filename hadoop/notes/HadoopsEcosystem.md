# An overview of Hadoop's Ecosystem

## Introduction
According to Hortonworks Hadoop is a:

> An open source software platform for distributed storage and distributed processing of very large data sets on computer clusters built from commodity hardware - Hortonworks

In other words Hadoop is used to handle Big Data when vertical scaling is insufficient to meet the client
needs to store and process data.

Why is vertical scaling not always an option? Hmm, probably upgrading your server's hardware has at some point no one to one relation with its performance. 
Eventually storing loads of data will increase disk seek times and thus also processing time. 
  
To better comprehend the need of horizontal scaling, distributed programming and thus technologies as Hadoop we should clarify the buzzword  **Big Data**!

> According to the course I'm following Big Data can be defined by 3 V's: Volume - Velocity - Variety 
> * Volume: Data that is too big (Terabytes) to store in tradition RDBMS or data records that are several Gigabytes on their own
> * Velocity:  Data that needs to be processed in real-time, because its value diminishes by the second
> * Variety: When the format of the data you are storing has no fixed format, but want to store these  either way 
to run analysis on it. You store for example everything the users sends you both structured/unstructured data to get insights out of your users data

>A RDBMS expert would probably argue that most of the V's can also be handled by traditional architectures where DB's run on a single node. 
And yes he/she might be right. 
This leaves us still with the big question when do we need to use Big Data technologies as Hadoop. A good rule of thumb 
can be if the cost of handling a certain task by one server 
is to high you should use horizontal scaling. Buying multiple small servers to do the job is sometimes worth the money 
over buying a full blown power server to handle the task on its own. 
Similar to buying two donkeys to pull the cartload instead of one big horse :)

>Hopefully during this journey of discovering Big Data technologies I will get a better grasp on what Big Data is and when to use its technologies for business purposes! 
Then I will come back to this section to elaborate things!


In this era, where information is expected to be a mouse click away and horizontal scaling is a fact through [HPS]AAS 
platforms (AWS, Azure, …) it is time to explore the Hadoop ecosystem and proactively find use cases for real word applications!

**HDSF** stands for the Hadoop Distributed File System and allows us to distribute the storage of big data 
across our cluster of computers so it makes all the hard drives look like one giant file system. 
Furthermore, if one of the computers burst into flames it will automatically recover so your data is not lost. 
 
**YARN** is Yet Another Resource Negotiator sitting on top of the HDFS and manages the resources on the cluster. 
It decides what node gets to run which task and when, what nodes are available for extra work and which nodes not.

Having HDFS to store data in a distributed way and YARN to manage the cluster resources this gives us an interesting 
setup for applications to run on. This brings us with the first application **MapReduce** that together with HDFS and 
YARN can be seen as Hadoop’s Core ecosystem. 

**MapReduce** is a programming model to process the data stored in the entire cluster by leveraging the cores of all the nodes. 
You can transform the data in parallel in an efficient way by using mappers, where reducers are used to aggregate data. 

Besides MapReduce Hadoop’s ecosystem contains a lot of applications that run on or alongside HDFS and Yarn. 
Think about Spark (better alternative of MapReduce), Pig, HBASE, Kafka, Tez, Hive, Flume, Kafka, ...

![alt text](https://github.com/msnm/DiscoveringBigData/raw/master/hadoop/notes/img/hadoopsecosystem.png "HadoopsEcosystem")










