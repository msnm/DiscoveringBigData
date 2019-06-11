package spark

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import spark.eeg.StimulusReaderSpark
import spark.exercises.{CustomerExpenditures, DegreesOfSeparation, MinTemperatures, MostPopularSuperHero, WordCount}

object SparkRunner {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(args(0))
    if(!conf.contains("spark.master")) {
      conf.setMaster("local[*]")
    }

    val sc = new SparkContext(conf)
    val file = new File(args(1))

    args(0) match {
      case "EEG" => this.EEG(file, sc, args(2))
      case "MIN_TMP" => MinTemperatures.findMinTempPerStation(file, sc)
      case "WC" => WordCount.countWords(file, sc)
      case "CUST_EXP" => CustomerExpenditures.sumCustomerExpendituresPerCustomer(file, sc)
      case "POP_HERO" => MostPopularSuperHero.findMostPopularSuperHero(file, new File(args(2)), sc)
      case "FIND_HERO" => DegreesOfSeparation.useBFSToFindDegreesOfSeparation(file, sc)
      case _ => print("Oops this sparkJob does not exist!")
    }

    Thread.sleep(300000)
  }

  private def EEG(file: File, sc: SparkContext, outputPath: String): Unit = {
    val lines: RDD[String] = StimulusReaderSpark.readEEGFile(file , sc, None)
    val header: Map[Int, String] = StimulusReaderSpark.retrieveHeader(lines)
    val eeg: Array[Array[String]] = StimulusReaderSpark.retrieveEEGLinesPerStimuli(lines).collect()

    eeg.foreach(stimulus => {
      val result: (String, Vector[String]) = StimulusReaderSpark.processEEGLinesPerStimuli(stimulus.toVector, header)
      val rdd: RDD[String] =  sc.parallelize(result._2)
      rdd.saveAsTextFile(outputPath.concat(File.separator).concat(result._1))
    })
  }

}

