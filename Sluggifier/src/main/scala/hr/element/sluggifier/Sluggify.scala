package hr.element.sluggifier

import com.ibm.icu.text.Transliterator



object Sluggify {
  def makeSlug(txt: String, replacement: String) = {
    //convert latin letters to ASCII (ex. đ->d)
    val txtTrans = transliterate(txt)
    //convert all to lower case
    val txtLower = txtTrans.toLowerCase
    //replace all unsafe and reserved characters
    convertCharacters(txtLower, replacement)
  }

  def transliterate(s: String) = {
    Transliterator.getInstance(TransliterateRules.latinToASCII).transliterate(s)
  }

/*
 * URL unsafe characters list
 * reserved: URLs use some characters for special use in defining their syntax.
 * reserved: $ & + , / : ; = ? @
 * unsafe:  Some characters present the possibility of being misunderstood within URLs
 * unsafe: space # % { } | \ ^ ~ [ ] ' "
 */
  def convertCharacters(txt: String, replacement: String) = {
    convertReservedCharacters(convertUnsafeCharacters(txt, replacement), replacement)
  }

  private def convertReservedCharacters(txt: String, replacement: String) =
    txt.replaceAll("[$,&,+,,,/,:,;,=,?,@]", replacement)

  private def convertUnsafeCharacters(txt: String, replacement: String) =
    txt.replaceAll("""[ ,#,%,{,},|,\\,^,~,[,],']""", replacement)

}