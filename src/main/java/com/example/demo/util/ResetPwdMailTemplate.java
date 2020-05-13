package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class ResetPwdMailTemplate {

    private String newMailTemplate = new String();

    private String mailTemplate = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
    "\n" + 
    "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" + 
    "<head>\n" + 
    "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\n" + 
    "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" + 
    "<meta content=\"width=device-width\" name=\"viewport\"/>\n" + 
    "<!--[if !mso]><!-->\n" + 
    "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\n" + 
    "<!--<![endif]-->\n" + 
    "<title></title>\n" + 
    "<!--[if !mso]><!-->\n" + 
    "<!--<![endif]-->\n" + 
    "<style type=\"text/css\">\n" + 
    "		body {\n" + 
    "			margin: 0;\n" + 
    "			padding: 0;\n" + 
    "		}\n" + 
    "\n" + 
    "		table,\n" + 
    "		td,\n" + 
    "		tr {\n" + 
    "			vertical-align: top;\n" + 
    "			border-collapse: collapse;\n" + 
    "		}\n" + 
    "\n" + 
    "		* {\n" + 
    "			line-height: inherit;\n" + 
    "		}\n" + 
    "\n" + 
    "		a[x-apple-data-detectors=true] {\n" + 
    "			color: inherit !important;\n" + 
    "			text-decoration: none !important;\n" + 
    "		}\n" + 
    "	</style>\n" + 
    "<style id=\"media-query\" type=\"text/css\">\n" + 
    "		@media (max-width: 660px) {\n" + 
    "\n" + 
    "			.block-grid,\n" + 
    "			.col {\n" + 
    "				min-width: 320px !important;\n" + 
    "				max-width: 100% !important;\n" + 
    "				display: block !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.block-grid {\n" + 
    "				width: 100% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.col {\n" + 
    "				width: 100% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.col>div {\n" + 
    "				margin: 0 auto;\n" + 
    "			}\n" + 
    "\n" + 
    "			img.fullwidth,\n" + 
    "			img.fullwidthOnMobile {\n" + 
    "				max-width: 100% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack .col {\n" + 
    "				min-width: 0 !important;\n" + 
    "				display: table-cell !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack.two-up .col {\n" + 
    "				width: 50% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack .col.num4 {\n" + 
    "				width: 33% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack .col.num8 {\n" + 
    "				width: 66% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack .col.num4 {\n" + 
    "				width: 33% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack .col.num3 {\n" + 
    "				width: 25% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack .col.num6 {\n" + 
    "				width: 50% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.no-stack .col.num9 {\n" + 
    "				width: 75% !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.video-block {\n" + 
    "				max-width: none !important;\n" + 
    "			}\n" + 
    "\n" + 
    "			.mobile_hide {\n" + 
    "				min-height: 0px;\n" + 
    "				max-height: 0px;\n" + 
    "				max-width: 0px;\n" + 
    "				display: none;\n" + 
    "				overflow: hidden;\n" + 
    "				font-size: 0px;\n" + 
    "			}\n" + 
    "\n" + 
    "			.desktop_hide {\n" + 
    "				display: block !important;\n" + 
    "				max-height: none !important;\n" + 
    "			}\n" + 
    "		}\n" + 
    "	</style>\n" + 
    "</head>\n" + 
    "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #FFFFFF;\">\n" + 
    "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" + 
    "<table bgcolor=\"#FFFFFF\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; width: 100%;\" valign=\"top\" width=\"100%\">\n" + 
    "<tbody>\n" + 
    "<tr style=\"vertical-align: top;\" valign=\"top\">\n" + 
    "<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\n" + 
    "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#FFFFFF\"><![endif]-->\n" + 
    "<div style=\"background-color:transparent;\">\n" + 
    "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 640px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #ebe7e2;\">\n" + 
    "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#ebe7e2;\">\n" + 
    "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:640px\"><tr class=\"layout-full-width\" style=\"background-color:#ebe7e2\"><![endif]-->\n" + 
    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"640\" style=\"background-color:#ebe7e2;width:640px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\n" + 
    "<div class=\"col num12\" style=\"min-width: 320px; max-width: 640px; display: table-cell; vertical-align: top; width: 640px;\">\n" + 
    "<div style=\"width:100% !important;\">\n" + 
    "<!--[if (!mso)&(!IE)]><!-->\n" + 
    "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n" + 
    "<!--<![endif]-->\n" + 
    "<div class=\"mobile_hide\">\n" + 
    "<div align=\"left\" class=\"img-container left autowidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"left\"><![endif]--><img alt=\"Image\" border=\"0\" class=\"left autowidth\" src=\"https://i.imgur.com/SLSQEmA.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 176px; display: block;\" title=\"Image\" width=\"176\"/>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<div class=\"mobile_hide\">\n" + 
    "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]--><img align=\"center\" alt=\"Alternate text\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/CPhUyE0.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 352px; display: block;\" title=\"Alternate text\" width=\"352\"/>\n" + 
    "<div style=\"font-size:1px;line-height:60px\"> </div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if !mso]><!-->\n" + 
    "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\n" + 
    "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n" + 
    "<div style=\"font-size:1px;line-height:30px\"> </div><img align=\"center\" alt=\"Alternate text\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/CPhUyE0.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 288px; display: block;\" title=\"Alternate text\" width=\"288\"/>\n" + 
    "<div style=\"font-size:1px;line-height:60px\"> </div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--<![endif]-->\n" + 
    "<div class=\"mobile_hide\">\n" + 
    "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n" + 
    "<div style=\"font-size:1px;line-height:10px\"> </div><img align=\"center\" alt=\"Alternate text\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/4FfM1qB.gif\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 256px; display: block;\" title=\"Alternate text\" width=\"256\"/>\n" + 
    "<div style=\"font-size:1px;line-height:30px\"> </div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if !mso]><!-->\n" + 
    "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\n" + 
    "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n" + 
    "<div style=\"font-size:1px;line-height:10px\"> </div><img align=\"center\" alt=\"Alternate text\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/4FfM1qB.gif\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 192px; display: block;\" title=\"Alternate text\" width=\"192\"/>\n" + 
    "<div style=\"font-size:1px;line-height:30px\"> </div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--<![endif]-->\n" + 
    "<div class=\"mobile_hide\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 0px; padding-bottom: 40px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#0c2e3a;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.5;padding-top:0px;padding-right:10px;padding-bottom:40px;padding-left:10px;\">\n" + 
    "<div style=\"font-size: 14px; line-height: 1.5; color: #0c2e3a; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 21px;\">\n" + 
    "<p style=\"font-size: 34px; line-height: 1.5; word-break: break-word; text-align: center; mso-line-height-alt: 51px; margin: 0;\"><span style=\"font-size: 34px;\"><strong>嗨～ </strong></span><span style=\"font-size: 34px;\"><strong>[user_name] </strong></span></p>\n" + 
    "<p style=\"font-size: 34px; line-height: 1.5; word-break: break-word; text-align: center; mso-line-height-alt: 51px; margin: 0;\"><span style=\"font-size: 34px;\"><strong>忘記密碼了齁ʕ •ᴥ•ʔ？</strong></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<!--[if !mso]><!-->\n" + 
    "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 0px; padding-bottom: 40px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#0c2e3a;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.5;padding-top:0px;padding-right:10px;padding-bottom:40px;padding-left:10px;\">\n" + 
    "<div style=\"font-size: 14px; line-height: 1.5; color: #0c2e3a; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 21px;\">\n" + 
    "<p style=\"font-size: 30px; line-height: 1.5; word-break: break-word; text-align: center; mso-line-height-alt: 45px; margin: 0;\"><span style=\"font-size: 30px;\"><span style=\"\"><strong>嗨～ </strong></span><span style=\"\"><strong>[user_name] </strong></span></span></p>\n" + 
    "<p style=\"font-size: 30px; line-height: 1.5; word-break: break-word; text-align: center; mso-line-height-alt: 45px; margin: 0;\"><span style=\"font-size: 30px;\"><strong>忘記密碼了齁ʕ •ᴥ•ʔ？</strong></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<!--<![endif]-->\n" + 
    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" + 
    "<tbody>\n" + 
    "<tr style=\"vertical-align: top;\" valign=\"top\">\n" + 
    "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 30px; padding-right: 30px; padding-bottom: 30px; padding-left: 30px;\" valign=\"top\">\n" + 
    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 2px solid #0C2E3A; width: 70%;\" valign=\"top\" width=\"70%\">\n" + 
    "<tbody>\n" + 
    "<tr style=\"vertical-align: top;\" valign=\"top\">\n" + 
    "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n" + 
    "</tr>\n" + 
    "</tbody>\n" + 
    "</table>\n" + 
    "</td>\n" + 
    "</tr>\n" + 
    "</tbody>\n" + 
    "</table>\n" + 
    "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 20px;padding-left: 20px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 20px;padding-left: 20px;\" align=\"center\"><![endif]-->\n" + 
    "<div style=\"font-size:1px;line-height:20px\"> </div><img align=\"center\" alt=\"Image\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/D2mN88E.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 128px; display: block;\" title=\"Image\" width=\"128\"/>\n" + 
    "<div style=\"font-size:1px;line-height:20px\"> </div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" + 
    "<div style=\"line-height: 1.2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" + 
    "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 28px; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><strong>來～新密碼在這兒～</strong></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" + 
    "<div style=\"line-height: 1.2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" + 
    "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 28px; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><strong>[new_password]</strong></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" + 
    "<tbody>\n" + 
    "<tr style=\"vertical-align: top;\" valign=\"top\">\n" + 
    "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 30px; padding-right: 30px; padding-bottom: 30px; padding-left: 30px;\" valign=\"top\">\n" + 
    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 2px solid #0C2E3A; width: 70%;\" valign=\"top\" width=\"70%\">\n" + 
    "<tbody>\n" + 
    "<tr style=\"vertical-align: top;\" valign=\"top\">\n" + 
    "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n" + 
    "</tr>\n" + 
    "</tbody>\n" + 
    "</table>\n" + 
    "</td>\n" + 
    "</tr>\n" + 
    "</tbody>\n" + 
    "</table>\n" + 
    "<div align=\"center\" class=\"button-container\" style=\"padding-top:5px;padding-right:20px;padding-bottom:35px;padding-left:20px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"><tr><td style=\"padding-top: 5px; padding-right: 20px; padding-bottom: 35px; padding-left: 20px\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://qrgosystem.nctu.me:3000\" style=\"height:39pt; width:177pt; v-text-anchor:middle;\" arcsize=\"20%\" stroke=\"false\" fillcolor=\"#ff823b\"><w:anchorlock/><v:textbox inset=\"0,0,0,0\"><center style=\"color:#ffffff; font-family:Tahoma, sans-serif; font-size:14px\"><![endif]--><a href=\"https://qrgosystem.nctu.me:3000\" style=\"-webkit-text-size-adjust: none; text-decoration: none; display: inline-block; color: #ffffff; background-color: #ff823b; border-radius: 10px; -webkit-border-radius: 10px; -moz-border-radius: 10px; width: auto; width: auto; border-top: 1px solid #ff823b; border-right: 1px solid #ff823b; border-bottom: 1px solid #ff823b; border-left: 1px solid #ff823b; padding-top: 10px; padding-bottom: 10px; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; text-align: center; mso-border-alt: none; word-break: keep-all;\" target=\"_blank\"><span style=\"padding-left:45px;padding-right:45px;font-size:14px;display:inline-block;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><strong><span data-mce-style=\"font-size: 14px; line-height: 28px;\" style=\"font-size: 14px; line-height: 28px;\">登入系統</span></strong></span></span></a>\n" + 
    "<!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<div class=\"mobile_hide\">\n" + 
    "<div align=\"right\" class=\"img-container right autowidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"right\"><![endif]--><img align=\"right\" alt=\"Image\" border=\"0\" class=\"right autowidth\" src=\"https://i.imgur.com/QRUrQE5.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 194px; float: none; display: block;\" title=\"Image\" width=\"194\"/>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if (!mso)&(!IE)]><!-->\n" + 
    "</div>\n" + 
    "<!--<![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
    "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<div style=\"background-color:transparent;\">\n" + 
    "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 640px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;\">\n" + 
    "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" + 
    "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:640px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" + 
    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"640\" style=\"background-color:transparent;width:640px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" + 
    "<div class=\"col num12\" style=\"min-width: 320px; max-width: 640px; display: table-cell; vertical-align: top; width: 640px;\">\n" + 
    "<div style=\"width:100% !important;\">\n" + 
    "<!--[if (!mso)&(!IE)]><!-->\n" + 
    "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" + 
    "<!--<![endif]-->\n" + 
    "<div class=\"mobile_hide\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" + 
    "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\n" + 
    "<p style=\"font-size: 13px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 16px; mso-ansi-font-size: 14px; margin: 0;\"><span style=\"font-size: 13px; mso-ansi-font-size: 14px;\"><strong>RollsUp</strong> class management system<strong><span style=\"color: #999999;\">   <span style=\"\">|</span>  </span>contact Email</strong> <a href=\"mailto:rollsupcontact@gmail.com\" style=\"text-decoration: underline; color: #343434;\" title=\"rollsupcontact@gmail.com\">rollsupcontact@gmail.com</a></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<!--[if !mso]><!-->\n" + 
    "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" + 
    "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\n" + 
    "<p style=\"font-size: 13px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 16px; mso-ansi-font-size: 14px; margin: 0;\"><span style=\"font-size: 13px; mso-ansi-font-size: 14px;\"><strong><span style=\"color: #999999;\">| </span>RollsUp</strong> class management system <span style=\"color: #808080;\"><strong>|</strong></span></span></p>\n" + 
    "<p style=\"font-size: 13px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 16px; mso-ansi-font-size: 14px; margin: 0;\"><span style=\"font-size: 13px; mso-ansi-font-size: 14px;\"><span style=\"color: #999999;\"><strong>| </strong></span><strong>contact Email</strong> <a href=\"mailto:rollsupcontact@gmail.com\" style=\"text-decoration: underline; color: #343434;\" title=\"rollsupcontact@gmail.com\">rollsupcontact@gmail.com</a> <strong><span style=\"color: #999999;\">|</span></strong></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<!--<![endif]-->\n" + 
    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" + 
    "<tbody>\n" + 
    "<tr style=\"vertical-align: top;\" valign=\"top\">\n" + 
    "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n" + 
    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 1px solid #EBE7E2; width: 100%;\" valign=\"top\" width=\"100%\">\n" + 
    "<tbody>\n" + 
    "<tr style=\"vertical-align: top;\" valign=\"top\">\n" + 
    "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n" + 
    "</tr>\n" + 
    "</tbody>\n" + 
    "</table>\n" + 
    "</td>\n" + 
    "</tr>\n" + 
    "</tbody>\n" + 
    "</table>\n" + 
    "<div class=\"mobile_hide\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" + 
    "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\n" + 
    "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">This email was sent from qrgomanager@gmail.com(do not reply)</span></p>\n" + 
    "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">by RollsUp, 37th IM Information System Project| Fu Jen University | Taiwan | New Taipei City | <a href=\"https://www.google.com.tw/maps/place/天主教輔仁大學/@25.0334487,121.4312869,17z/data=!3m1!4b1!4m5!3m4!1s0x3442a7dd8be91eaf:0xe342a67d6574f896!8m2!3d25.0334487!4d121.4334756?hl=zh-TW\" rel=\"noopener\" style=\"text-decoration: underline; color: #343434;\" target=\"_blank\">242062</a></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<!--[if !mso]><!-->\n" + 
    "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\n" + 
    "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" + 
    "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" + 
    "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\n" + 
    "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">This email was sent from</span></p>\n" + 
    "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">qrgomanager@gmail.com(do not reply) </span><span style=\"font-size: 11px;\">by RollsUp</span></p>\n" + 
    "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">| 37th IM Information System Project | </span></p>\n" + 
    "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">| Fu Jen University | Taiwan | New Taipei City | <a href=\"https://www.google.com.tw/maps/place/天主教輔仁大學/@25.0334487,121.4312869,17z/data=!3m1!4b1!4m5!3m4!1s0x3442a7dd8be91eaf:0xe342a67d6574f896!8m2!3d25.0334487!4d121.4334756?hl=zh-TW\" rel=\"noopener\" style=\"text-decoration: underline; color: #343434;\" target=\"_blank\">242062</a></span></p>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if mso]></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "<!--<![endif]-->\n" + 
    "<!--[if (!mso)&(!IE)]><!-->\n" + 
    "</div>\n" + 
    "<!--<![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
    "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "</div>\n" + 
    "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
    "</td>\n" + 
    "</tr>\n" + 
    "</tbody>\n" + 
    "</table>\n" + 
    "<!--[if (IE)]></div><![endif]-->\n" + 
    "</body>\n" + 
    "</html>";






    public void setUserName(String user_name){
        newMailTemplate = mailTemplate.replace("[user_name]", user_name);
    }

    public void setNewPassword(String new_password){
        newMailTemplate = newMailTemplate.replace("[new_password]", new_password);
    }

    public String getNewMailTemplate() {
        return this.newMailTemplate;
    }

    // public void setNewMailTemplate(String newMailTemplate) {
    //     this.newMailTemplate = newMailTemplate;
    // }







    
}