package hr.element.etb.slug
package test

import org.scalatest._
import org.scalatest.matchers._



class SMSSpec extends FeatureSpec with GivenWhenThen with MustMatchers{

  feature("SMS URL sanitization"){
    info("SMS must convert every evil character combination into a pretty URL-safe string")

    scenario("SMS unsafe character removal"){
      val in = "aZ{}[]! a";
      given ("a string containing: %s" format in)
      val res = "aZ-! a"
      then ("it should return a string: %s" format res)
      val out = SMSifier(in)
      out must equal (res)
    }

    scenario("SMS unsafe character trimming"){
      val in = "!aćsčš ";
      given ("a string containing: %s" format in)
      val res = "!acscs "
      then ("it should return a string: %s" format res)
      val out = SMSifier(in)
      out must equal (res)
    }

    scenario("SMS whitespace trimming to one character"){
      val in = "Helooooooooooooo   oooo";
      given ("a string containing: %s" format in)
      val res = "Helooooooooooooo oooo";
      then ("it should return a string: %s" format res)
      val out = SMSifier(in)
      out must equal (res)
    }
  }

}