<%



'----function that removes html tags-----------
   Function RemoveHTML( strText )
		Dim RegEx
		Set RegEx = New RegExp
		RegEx.Pattern = "<[^>]*>"
		RegEx.Global = True
		RemoveHTML = RegEx.Replace(strText, "")
	End Function
'---------------------------------------------
   
'------defining script vars-------------------
   Dim mailObj, mailCfg, myBody, fld, subj, mail_from, mail_to, smtp_server, smtp_port, plain_text

	Dim RegEx 
    set RegEx = New RegExp
'--------------------------------------------


'----Settings-----------
subj = "Contact form from your site"
mail_from = "admin@somehost.com"
mail_to = "test@test.com"
smtp_server = "localhost"
smtp_port = 25
plain_text = "false"

'------getting data sent by site (filtering configuration data)------------
   For Each fld in Request.Form
      If Request.Form(fld) <> "" and _
      fld <> "mail_to" and _
	  fld <> "smtp_server" and _
	  fld <> "smtp_port" and _
	  fld <> "plain_text" and _
	  fld <> "mail_from" and _
      fld <> "mail_subject" Then
         myBody = myBody & vbCRLF & "   <b>" & fld & "</b> :<br/> " & Trim(Request.Form(fld)) & "<br/>"
      End If
   Next
'---------------------------------------------------------------------------

'----------setting conf data------------------------------------------------
	On Error Resume Next
		Set myMail = CreateObject("CDO.Message") 
		myMail.Subject = subj
		myMail.From =mail_from
		myMail.To = mail_to
		
'--------if plain text is set to true removing html---------------------------------------		
		if plain_text = "true" then 
			
			myMail.TextBody = RemoveHTML(myBody)

'-------otherwise composing message body--------------------------------------------------			
			else myMail.HTMLBody = "<html><body>" & myBody & "</body></html>"
			
		end if
		
		
'----------setting configuration params for smtp----------------------------------------------------------------------------------
		myMail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/sendusing") = 1
		myMail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/smtpserver") = smtp_server
		myMail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/smtpserverport") = smtp_port
		myMail.Configuration.Fields.Update 
'---------------------------------------------------------------------------------------------------------------------------------		
		myMail.Send '---------------sending message
  
   If Err = 0 Then
   Response.Write("The message has been sent, thank you") 'if there the message is sent return 1 to flash
   Else
   Response.Write("Message could not be sent") 'otherwise return 0
   End If
   
%>

