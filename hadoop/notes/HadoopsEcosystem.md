# An overview of Hadoop's Ecosystem

## Introduction
According to Hortonworks Hadoop is a:

> An open source software platform for distributed storage and distributed processing of very large data sets on computer clusters built from commodity hardware - Hortonworks

In other words Hadoop is used to handle Big Data when vertical scaling is insufficient to meet the client
needs to store and process data.

Why is vertical scaling not always an option? Hmm, probably upgrading your server's hardware has at some point no one to one relation to its performance upgrade. 
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
Picture is taken from the Udemy [course](https://www.udemy.com/the-ultimate-hands-on-hadoop-tame-your-big-data)

## Hadoop's File System (HDFS)

HDFS is a distributed file system that is not made for handling tons of files of just several mb, 
but it is designed to store very large files that can be 
partitioned into chunks of 120mb. Each of these blocks will be persisted on different nodes and more than one copy is stored so that 
if one node goes down your data is not lost (failover). It is the Name Node (a virtual directory structure) that keeps 
track where your blocks of data live. This Name Node has a ledger called the edit log that maintains a record of 
what’s being created, modified and stored. 

Let’s say you want to read a file from the HDFS, you (Client Node/App) will first talk to the Name Node, which will look 
into its ledger which Data Nodes you should access to retrieve your data in the most efficient manner. 
Then you will retrieve the data blocks from the given Data Nodes (one and three in left the figure). 

![alt text](https://github.com/msnm/DiscoveringBigData/raw/master/hadoop/notes/img/read_write_hfds.png "Left: reading from HDFS, Right: writing to HDFS") 

To store a file on HDFS your client will first talk to the Name Node which will make an entry into its ledger for 
this new file. Then you talk to a single Data Node to store your file and this Data Node will talk to its peer Data Nodes 
to store your data in blocks in a replicated manner. Each Data Node sends an acknowledgment down the road back to the client. 
Ultimately the client talks to the Name Node again which stores the info of where each data block is stored into the entry 
created for this file in its ledger. 

HDFS is designed to *write-once and read many times*, meaning that it is not intended for OLTP but rather for OLAP. As we will discover later the main purpose of the applications
that run on top of HDFS and YARN is to analyse the data stored on a HFDS and not to alter/update the data. 

So far, our data is stored in a distributed and replicated manner, but what happens if our Name Node crashes? 
To eliminate this single point of failure several options can be used depending on your business requirements: 
1)	Copy the ledger constantly (edit logs) to an NFS backup. If your node crashes, you create a new Name Node and 
    initialise it with the edit logs stored on the NFS. This option comes with downtime and if there is some network I/O lag you might lose some data. 
2)	Create a non-active secondary Name Node that constantly merges the edit log of the active Name Node. 
    This is not an automatic failover mechanism, but this should reduce the loss of data.
3)	Store the edit log on a non HDFS like NFS where both the active and passive Name Nodes have access to the shared 
    edit log. When the master crashes the slave takes over and no data is lost. It is Zookeeper that handles the failover mechanism. 
    This setup is more complicated, and the possibility exist that two Name Nodes are active if the setup is configured badly.

Accessing the HDFS (talking to the Name Node) can be done using a UI, CLI, HTTP/ HFDS Proxies, Java interface and you can even mount your HDFS like NFS Gateway.
Creating a new dir on a HDFS can be done on a CLI like this: 
> hadoop fs -mkdir tmp

Most of the Hadoop's file system commands look similar to the Linux commands. In an upcoming adventure I hope to experiment more
 in a hands on way with the HDFS, so for now I will not explain the different commands and how to create/access a HDFS.
  
![alt text](https://github.com/msnm/DiscoveringBigData/raw/master/hadoop/notes/img/architecture_hdfs.png "Basic architecture sketch of a HDFS") 

I have not explored yet what types of data formats you can store on top of a HDFS. I know that you can just have normal files on it like text and images and process them, but you can also run NoSQL databases on HDFS. The thing is if you want to search specific things within your files you need to do a full table scan. In otherwords scanning every line of your files and if they are several GB big it is a slow operation... If you need low latency and fast random read/write access you can opt for HBASE, Parquet, or Cassandra or ... 

HBASE for example is a columnar DB, where you are not bound to a strict schema. The advantage of columnar DB's is when you need to read millions of lines of data in a table, but only need for example only two columns out of seven columns specified in the table for you query you will store these columns together with a row id and probably a versioning number. This way you can have history. The difference with RDBMS is that instead of reading from left to right row by row it reads only the columns you need and does this from top to bottom. [Simple basic video illustration of the concept.](https://www.youtube.com/watch?v=8KGVFB3kVHQ)

A disadvantage of for example HBASE as that you cannot join tables ... 




More to come here ...  


## Yarn

Yarn is a distributed OS and its purpose is to allocate resources so that applications can parallelize their tasks on different nodes. 
The **resource manager** is responsible for allocating the resources demanded by **application managers/masters**. The latter are
for example Spark or MapReduce jobs that want to execute a job on the cluster. More about Spark later on.  

![alt text](https://github.com/msnm/DiscoveringBigData/raw/master/hadoop/notes/img/yarn.png "Basic architecture sketch of a HDFS") 

In the above figure a client asks to the **resource manager** to start for example a Spark Application (1). 
A container will be allocated on one of the nodes in the cluster where the **application master** can run in (2). 
The **application master** will examine the job it wants to execute and decides which tasks of the job can be executed in parallel (3).
Then this will be communicated back to the **resource manager** who allocates the tasks to nodes. Each task will run in 
a container (own network, memory, cpu) and communicates with the **application master** (4). Each node has also a **node manager** 
which checks the status/health of that node and communicates with the **resource manager**. This way the **resource manager** knows which
nodes are failing, which can run an extra task etc (5). This is in a nutshell how I think Yarn or other distributed OS like Mesos work.  
 
More to come here ...

## Apache Spark

> Currently I'm learning from the following sources to retrieve insights in Apache Spark: 
>* a gitbook to understand more of [Sparks Internals](https://jaceklaskowski.gitbooks.io/mastering-apache-spark/)
>* Udemy [course](https://www.udemy.com/apache-spark-with-scala-hands-on-with-big-data)
>* Clear explanation of [Spark Components](https://www.youtube.com/watch?v=m4pYYnY4_gU&list=PLFxgwFEQig6wWDHq3iMfjm5ZCHs_UdIp7)
>* Thus more come

According to the official Apache Spark website:

> Apache Spark is a unified analytics engine for large-scale processing

In simple terms if you want to run your code written in Java/Python/Scala to run in distributed way you need to use
the Spark API. Instead of using your Scala map/reduce/flatMap/groupBy functions you will use the Spark implementations.
To see my code quests I tried to resolve with Spark go to [this repo](https://github.com/msnm/DiscoveringBigData/tree/master/spark/code/helloworld/src/main/scala/spark/exercises)

Before continuing I want to note that I will not explore MapReduce, because I've read that it's  legacy software and should not be used anymore for new projects. Spark seems much faster according to several articles I have read. The key difference why Spark is faster is that it stores the subresults of its computations in memory where MapReduce stores these on disk which is much slower. And also the introduction of DAG gives spark the opportunity to optimize its executionplan.

**RDD** stands for Resilient Distributed Dataset and is an immutuable (virtual) distributed collection of objects. 
The data stored on HDFS that you will transform with Spark will be loaded as an RDD. This RDD can be seen as a virtual array of your data, which has n partitions where the partitions are divided over the nodes on the cluster. 

**Transformations** are functions like map, filter, distinct, groupByKey, ... etc and differ from **Actions** as reduce, collect, count, first, take ... in the sense that transformations describe how the RDD should be altered, whereas actions are like triggers which will execute the previously defined transformations and retrieve the data. 

**DAG** https://data-flair.training/blogs/dag-in-apache-spark/

**Shuffle**

**Datasets**


**Streaming**

**Structured Streaming*** 










