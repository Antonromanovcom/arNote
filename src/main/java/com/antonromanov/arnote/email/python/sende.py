import smtplib

class em():
    def greet(self):
        to = 'antonr0manov@yandex.ru'
        gmail_user = 'ant0nr0manov@rambler.ru'
        gmail_pwd = 'Y2V3p9bSGQEfHdZ'
        smtpserver = smtplib.SMTP("smtp.rambler.ru",587)
        smtpserver.ehlo()
        smtpserver.starttls()
        smtpserver.ehlo
        smtpserver.login(gmail_user, gmail_pwd)
        header = 'To:' + to + '\n' + 'From: ' + gmail_user + '\n' + 'Subject:testing \n'
        print (header)
        msg = header + '\n this is test msg from mkyong.com \n\n'
        smtpserver.sendmail(gmail_user, to, msg)
        print ('done!')
        smtpserver.close()

a = em()
a.greet()