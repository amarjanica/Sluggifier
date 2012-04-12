package hr.element.etb.slug
package test

import org.scalatest._
import org.scalatest.matchers._

class SluggifySpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  feature("Sluggify URL sanitization"){
    info("Sluggify must convert every evil character combination into a pretty URL-safe string")

    scenario("Uppercase character lowercasing"){
      val in = 'A' to 'Z' mkString;
      given ("a string containing uppercase letters: %s" format in)
      val res = in.toLowerCase
      then ("it should return a string with no uppercase characters: %s" format res)
      val out = Sluggifier(in)
      out must equal (res)
    }

    scenario("URL unsafe character removal"){
      val in = """!"#$%&'()*+,/:;<=>?@[\]^`{|}~""" mkString;
      given ("a string containing only non-safe characters: %s" format in)
      then ("it should return an empty string.")
      val out = Sluggifier(in)
      out must be ('empty)
    }

    scenario("URL unsafe character trimming"){
      val in = """~ Dinamo ! Is the best! ~""" mkString;
      given ("an URL unsafe string: %s" format in)
      val res = "dinamo-is-the-best"
      then ("the resulting slug must be trimmed: %s" format res)
      val out = Sluggifier(in)
      out must equal (res)
    }

    scenario("URL unsafe character trimming2"){
      val in = """~ $Hajduk ! Is better!!!Slayer rules$ ~""" mkString;
      given ("an URL unsafe string: %s" format in)
      val res = "hajduk-is-better-slayer-rules"
      then ("the resulting slug must be trimmed: %s" format res)
      val out = Sluggifier(in)
      out must equal (res)
    }
  }
}
