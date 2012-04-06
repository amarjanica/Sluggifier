package hr.element.etb.slug

import com.ibm.icu.text.Transliterator
import java.util.regex.Pattern

object Sluggifier {
  protected val URLUnsafeChars =
    "[^-._a-z0-9]+"

  private val default =
    new Sluggifier(TransliterateRules.latinToASCII, "-")

  def apply(text: String) = default(text)
}

case class Sluggifier(transRules: String, replacement: String) {
  private val Trans = Transliterator.getInstance(transRules)

  private val URLUnsafeReplacePattern =
    "(%s|%s)+" format(Sluggifier.URLUnsafeChars, Pattern.quote(replacement)) r

  private val URLUnsafeTrimPattern =
    "^%s|%1$s$" format(Sluggifier.URLUnsafeChars) r

  // convert latin letters to ASCII (ex. đ->d)
  protected val transliterate =
    Trans.transliterate(_: String)

  // convert all to lower case
  protected val lowerCase =
    (_: String).toLowerCase

  // trim all URL non-safe characters from the beginning and the end
  protected val trim =
    URLUnsafeTrimPattern.replaceAllIn(_: String, "")

  // replace all URL non-safe characters
  protected val sanitize =
    URLUnsafeReplacePattern.replaceAllIn(_: String, replacement)

  // do all from above
  def apply(text: String) =
    (transliterate andThen lowerCase andThen trim andThen sanitize)(text)
}
