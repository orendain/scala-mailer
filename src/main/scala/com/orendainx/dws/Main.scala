package com.orendainx.dws

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import com.github.tototoshi.csv.{CSVReader, DefaultCSVFormat}
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import org.simplejavamail.mailer.config.TransportStrategy


object Main {

  def main(args: Array[String]): Unit = {

    implicit object MyFormat extends DefaultCSVFormat {
      override val delimiter = ';'
    }

    val reader = CSVReader.open(new File(args(0)))

    val textTemplate = new String(Files.readAllBytes(Paths.get(args(1))), StandardCharsets.UTF_8)

    println("Enter your email password.")
    println("Keystrokes are not displayed and nothing is saved: ")
    val passwd = System.console().readPassword().toString

    val mailer = MailerBuilder
      .withSMTPServer("west.exch080.serverdata.net", 587, "eorendain@hortonworks.com", passwd)
      .withTransportStrategy(TransportStrategy.SMTP_TLS)
      .buildMailer()

    reader.toStreamWithHeaders.foreach { e =>

//      val m = Pattern.compile("\\{([^)]+)\\}").matcher(textTemplate)
//      var newText = textTemplate
//      while(m.find()) {
//        val tk = m.group(1)
//        newText = newText.replace(s"{$tk}", e(tk))
//      }

      val newText = textTemplate
        .replace("{Email}", e("Email"))
        .replace("{First Name}", e("First Name"))
        .replace("{Abstract Title}", e("Abstract Title"))
        .replace("{Conference Track}", e("Conference Track"))

      mailer.sendMail(EmailBuilder.startingBlank()
        .from("Edgar Orendain", "eorendain@hortonworks.com")
        .to(e("First Name"), e("Email"))
        .withSubject("Subject Goes Here")
        .withPlainText(newText)
        .buildEmail())
    }

    reader.close()
  }

}
