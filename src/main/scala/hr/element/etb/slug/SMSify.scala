package hr.element.etb
package slug

import com.ibm.icu.text.Transliterator
import java.util.regex.Pattern



object SMSifier {
  protected val SMSUnsafeChars = """[^!-/0-9:-@A-Za-z£¥\n\s]+"""

  private val default =
    new SMSifier(TransliterateRules.latinToASCII, "-")

  def apply(text: String) = default(text)
}


case class SMSifier (transRules: String, replacement: String) {
  private val Trans = Transliterator.getInstance(transRules)

  private val SMSUnsafeReplacePattern =
    "(%s|%s)+" format(SMSifier.SMSUnsafeChars, Pattern.quote(replacement)) r

  private val SMSUnsafeTrimPattern =
    "^%s|%1$s$" format(SMSifier.SMSUnsafeChars) r

  private val SMSWhiteSpace =
    """\s+"""r

  // convert latin letters to ASCII (ex. đ->d)
  protected val transliterate =
    Trans.transliterate(_: String)

  // trim all SMS non-safe characters from the beginning and the end
  protected val trim =
    SMSUnsafeTrimPattern.replaceAllIn(_: String, "")

  protected val trimWhiteSpaceToOne =
    SMSWhiteSpace.replaceAllIn(_: String, " ")

  // replace all SMS non-safe characters
  protected val sanitize =
    SMSUnsafeReplacePattern.replaceAllIn(_: String, replacement)

  def apply(text: String) = {
    (transliterate andThen trim andThen trimWhiteSpaceToOne andThen sanitize)(text)
  }
}