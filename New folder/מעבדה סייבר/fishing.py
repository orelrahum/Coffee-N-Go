import win32com.client as win32
import sys
username=sys.argv[1]
mailservice=sys.argv[2]
job_title=sys.argv[3]
email=username+'@'+mailservice

outlook = win32.Dispatch('outlook.application')
mail = outlook.CreateItem(0)
mail.To = email
mail.Subject = job_title
mail.Body = 'click here if you want a million dollar'
#mail.HTMLBody = '<h2>HTML Message body</h2>' #this field is optional

# To attach a file to the email (optional):
attachment  = "C:\\Users\\barge\\Desktop\\check.py"
mail.Attachments.Add(attachment)

mail.Send()