package com.example.demo.util.MailTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class ResetPwdMailTemplateAnnouncement {

    private String newMailTemplate = new String();

    private String mailTemplate = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
    + "\r\n"
    + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\r\n"
    + "<head>\r\n"
    + "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\r\n"
    + "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\r\n"
    + "<meta content=\"width=device-width\" name=\"viewport\"/>\r\n" + "<!--[if !mso]><!-->\r\n"
    + "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\r\n" + "<!--<![endif]-->\r\n"
    + "<title></title>\r\n" + "<!--[if !mso]><!-->\r\n" + "<!--<![endif]-->\r\n"
    + "<style type=\"text/css\">\r\n" + "		body {\r\n" + "			margin: 0;\r\n"
    + "			padding: 0;\r\n" + "		}\r\n" + "\r\n" + "		table,\r\n" + "		td,\r\n"
    + "		tr {\r\n" + "			vertical-align: top;\r\n" + "			border-collapse: collapse;\r\n"
    + "		}\r\n" + "\r\n" + "		* {\r\n" + "			line-height: inherit;\r\n" + "		}\r\n"
    + "\r\n" + "		a[x-apple-data-detectors=true] {\r\n" + "			color: inherit !important;\r\n"
    + "			text-decoration: none !important;\r\n" + "		}\r\n" + "	</style>\r\n"
    + "<style id=\"media-query\" type=\"text/css\">\r\n" + "		@media (max-width: 660px) {\r\n"
    + "\r\n" + "			.block-grid,\r\n" + "			.col {\r\n"
    + "				min-width: 320px !important;\r\n" + "				max-width: 100% !important;\r\n"
    + "				display: block !important;\r\n" + "			}\r\n" + "\r\n"
    + "			.block-grid {\r\n" + "				width: 100% !important;\r\n" + "			}\r\n"
    + "\r\n" + "			.col {\r\n" + "				width: 100% !important;\r\n" + "			}\r\n"
    + "\r\n" + "			.col>div {\r\n" + "				margin: 0 auto;\r\n" + "			}\r\n"
    + "\r\n" + "			img.fullwidth,\r\n" + "			img.fullwidthOnMobile {\r\n"
    + "				max-width: 100% !important;\r\n" + "			}\r\n" + "\r\n"
    + "			.no-stack .col {\r\n" + "				min-width: 0 !important;\r\n"
    + "				display: table-cell !important;\r\n" + "			}\r\n" + "\r\n"
    + "			.no-stack.two-up .col {\r\n" + "				width: 50% !important;\r\n"
    + "			}\r\n" + "\r\n" + "			.no-stack .col.num4 {\r\n"
    + "				width: 33% !important;\r\n" + "			}\r\n" + "\r\n"
    + "			.no-stack .col.num8 {\r\n" + "				width: 66% !important;\r\n" + "			}\r\n"
    + "\r\n" + "			.no-stack .col.num4 {\r\n" + "				width: 33% !important;\r\n"
    + "			}\r\n" + "\r\n" + "			.no-stack .col.num3 {\r\n"
    + "				width: 25% !important;\r\n" + "			}\r\n" + "\r\n"
    + "			.no-stack .col.num6 {\r\n" + "				width: 50% !important;\r\n" + "			}\r\n"
    + "\r\n" + "			.no-stack .col.num9 {\r\n" + "				width: 75% !important;\r\n"
    + "			}\r\n" + "\r\n" + "			.video-block {\r\n"
    + "				max-width: none !important;\r\n" + "			}\r\n" + "\r\n"
    + "			.mobile_hide {\r\n" + "				min-height: 0px;\r\n"
    + "				max-height: 0px;\r\n" + "				max-width: 0px;\r\n"
    + "				display: none;\r\n" + "				overflow: hidden;\r\n"
    + "				font-size: 0px;\r\n" + "			}\r\n" + "\r\n" + "			.desktop_hide {\r\n"
    + "				display: block !important;\r\n" + "				max-height: none !important;\r\n"
    + "			}\r\n" + "		}\r\n" + "	</style>\r\n" + "</head>\r\n"
    + "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #FFFFFF;\">\r\n"
    + "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\r\n"
    + "<table bgcolor=\"#FFFFFF\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; width: 100%;\" valign=\"top\" width=\"100%\">\r\n"
    + "<tbody>\r\n" + "<tr style=\"vertical-align: top;\" valign=\"top\">\r\n"
    + "<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\r\n"
    + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#FFFFFF\"><![endif]-->\r\n"
    + "<div style=\"background-color:transparent;\">\r\n"
    + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 640px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #ebe7e2;\">\r\n"
    + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#ebe7e2;\">\r\n"
    + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:640px\"><tr class=\"layout-full-width\" style=\"background-color:#ebe7e2\"><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"640\" style=\"background-color:#ebe7e2;width:640px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\r\n"
    + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 640px; display: table-cell; vertical-align: top; width: 640px;\">\r\n"
    + "<div style=\"width:100% !important;\">\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n"
    + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\r\n"
    + "<!--<![endif]-->\r\n" + "<div class=\"mobile_hide\">\r\n"
    + "<div align=\"left\" class=\"img-container left autowidth\" style=\"padding-right: 0px;padding-left: 0px;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"left\"><![endif]--><img alt=\"Image\" border=\"0\" class=\"left autowidth\" src=\"https://i.imgur.com/SLSQEmA.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 176px; display: block;\" title=\"Image\" width=\"176\"/>\r\n"
    + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n" + "</div>\r\n"
    + "<div class=\"mobile_hide\">\r\n"
    + "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]--><img align=\"center\" alt=\"Alternate text\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/CPhUyE0.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 352px; display: block;\" title=\"Alternate text\" width=\"352\"/>\r\n"
    + "<div style=\"font-size:1px;line-height:60px\"> </div>\r\n"
    + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n" + "</div>\r\n"
    + "<!--[if !mso]><!-->\r\n"
    + "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\r\n"
    + "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\r\n"
    + "<div style=\"font-size:1px;line-height:30px\"> </div><img align=\"center\" alt=\"Alternate text\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/CPhUyE0.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 288px; display: block;\" title=\"Alternate text\" width=\"288\"/>\r\n"
    + "<div style=\"font-size:1px;line-height:60px\"> </div>\r\n"
    + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n" + "</div>\r\n"
    + "<!--<![endif]-->\r\n"
    + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\r\n"
    + "<tbody>\r\n" + "<tr style=\"vertical-align: top;\" valign=\"top\">\r\n"
    + "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 30px; padding-right: 30px; padding-bottom: 50px; padding-left: 30px;\" valign=\"top\">\r\n"
    + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 2px solid #0C2E3A; width: 90%;\" valign=\"top\" width=\"90%\">\r\n"
    + "<tbody>\r\n" + "<tr style=\"vertical-align: top;\" valign=\"top\">\r\n"
    + "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\r\n"
    + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n" + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
    + "</table>\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n" + "</div>\r\n" + "<!--<![endif]-->\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<div style=\"background-color:transparent;\">\r\n"
    + "<div class=\"block-grid mixed-two-up\" style=\"Margin: 0 auto; min-width: 320px; max-width: 640px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #ebe7e2;\">\r\n"
    + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#ebe7e2;\">\r\n"
    + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:640px\"><tr class=\"layout-full-width\" style=\"background-color:#ebe7e2\"><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"213\" style=\"background-color:#ebe7e2;width:213px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\r\n"
    + "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 212px; width: 213px;\">\r\n"
    + "<div style=\"width:100% !important;\">\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n"
    + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\r\n"
    + "<!--<![endif]-->\r\n"
    + "<div align=\"center\" class=\"img-container center fixedwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]--><img align=\"center\" alt=\"Alternate text\" border=\"0\" class=\"center fixedwidth\" src=\"https://i.imgur.com/jPpSlGu.gif\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 159px; display: block;\" title=\"Alternate text\" width=\"159\"/>\r\n"
    + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n"
    + "</div>\r\n" + "<!--<![endif]-->\r\n" + "</div>\r\n" + "</div>\r\n"
    + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"426\" style=\"background-color:#ebe7e2;width:426px; border-top: 0px dashed transparent; border-left: 0px dashed transparent; border-bottom: 0px dashed transparent; border-right: 0px dashed transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\r\n"
    + "<div class=\"col num8\" style=\"display: table-cell; vertical-align: top; min-width: 320px; max-width: 424px; width: 426px;\">\r\n"
    + "<div style=\"width:100% !important;\">\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n"
    + "<div style=\"border-top:0px dashed transparent; border-left:0px dashed transparent; border-bottom:0px dashed transparent; border-right:0px dashed transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\r\n"
    + "<!--<![endif]-->\r\n" + "<!--[if !mso]><!-->\r\n"
    + "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 50px; padding-top: 50px; padding-bottom: 50px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:50px;padding-right:50px;padding-bottom:50px;padding-left:50px;\">\r\n"
    + "<div style=\"line-height: 1.2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\r\n"
    + "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 26px; mso-line-height-alt: 31px; margin: 0;\"><span style=\"font-size: 26px; color: #000000;\">\"[cs_name]\"</span></p>\r\n"
    + "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 26px; mso-line-height-alt: 31px; margin: 0;\"><span style=\"font-size: 26px; color: #000000;\">剛剛新增公告囉～</span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--<![endif]-->\r\n" + "<div class=\"mobile_hide\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 20px; padding-top: 45px; padding-bottom: 50px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:45px;padding-right:50px;padding-bottom:50px;padding-left:20px;\">\r\n"
    + "<div style=\"line-height: 1.2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\r\n"
    + "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 30px; mso-line-height-alt: 36px; margin: 0;\"><span style=\"font-size: 30px; color: #000000;\">\"[cs_name]\"</span></p>\r\n"
    + "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 30px; mso-line-height-alt: 36px; margin: 0;\"><span style=\"font-size: 30px; color: #000000;\">剛剛新增公告囉～</span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--[if (!mso)&(!IE)]><!-->\r\n" + "</div>\r\n" + "<!--<![endif]-->\r\n" + "</div>\r\n"
    + "</div>\r\n" + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<div style=\"background-color:transparent;\">\r\n"
    + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 640px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #ebe7e2;\">\r\n"
    + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#ebe7e2;\">\r\n"
    + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:640px\"><tr class=\"layout-full-width\" style=\"background-color:#ebe7e2\"><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"640\" style=\"background-color:#ebe7e2;width:640px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\r\n"
    + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 640px; display: table-cell; vertical-align: top; width: 640px;\">\r\n"
    + "<div style=\"width:100% !important;\">\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n"
    + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\r\n"
    + "<!--<![endif]-->\r\n" + "<div class=\"mobile_hide\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 40px; padding-left: 40px; padding-top: 10px; padding-bottom: 30px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:2;padding-top:10px;padding-right:40px;padding-bottom:30px;padding-left:40px;\">\r\n"
    + "<div style=\"line-height: 2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 24px;\">\r\n"
    + "<p style=\"text-align: center; line-height: 2; word-break: break-word; font-size: 28px; mso-line-height-alt: 56px; margin: 0;\"><span style=\"font-size: 28px;\"><strong><span style=\"\">[at_title]</span></strong></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--[if !mso]><!-->\r\n"
    + "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 40px; padding-left: 40px; padding-top: 10px; padding-bottom: 30px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:2;padding-top:10px;padding-right:40px;padding-bottom:30px;padding-left:40px;\">\r\n"
    + "<div style=\"line-height: 2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 24px;\">\r\n"
    + "<p style=\"text-align: center; line-height: 2; word-break: break-word; font-size: 24px; mso-line-height-alt: 48px; margin: 0;\"><span style=\"font-size: 24px;\"><strong><span style=\"\">[at_title]</span></strong></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--<![endif]-->\r\n" + "<div class=\"mobile_hide\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:30px;padding-bottom:10px;padding-left:30px;\">\r\n"
    + "<div style=\"line-height: 1.2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\r\n"
    + "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 24px; mso-line-height-alt: 29px; margin: 0;\"><span style=\"font-size: 24px;\"><strong>[at_content]</strong></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--[if !mso]><!-->\r\n"
    + "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:30px;padding-bottom:10px;padding-left:30px;\">\r\n"
    + "<div style=\"line-height: 1.2; font-size: 12px; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\r\n"
    + "<p style=\"text-align: center; line-height: 1.2; word-break: break-word; font-size: 22px; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 22px;\"><strong>[at_content]</strong></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--<![endif]-->\r\n"
    + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\r\n"
    + "<tbody>\r\n" + "<tr style=\"vertical-align: top;\" valign=\"top\">\r\n"
    + "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 50px; padding-right: 30px; padding-bottom: 30px; padding-left: 30px;\" valign=\"top\">\r\n"
    + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 2px solid #0C2E3A; width: 90%;\" valign=\"top\" width=\"90%\">\r\n"
    + "<tbody>\r\n" + "<tr style=\"vertical-align: top;\" valign=\"top\">\r\n"
    + "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\r\n"
    + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n" + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
    + "</table>\r\n"
    + "<div align=\"center\" class=\"button-container\" style=\"padding-top:5px;padding-right:20px;padding-bottom:35px;padding-left:20px;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"><tr><td style=\"padding-top: 5px; padding-right: 20px; padding-bottom: 35px; padding-left: 20px\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://qrgosystem.nctu.me:3000\" style=\"height:39pt; width:177pt; v-text-anchor:middle;\" arcsize=\"20%\" stroke=\"false\" fillcolor=\"#ff823b\"><w:anchorlock/><v:textbox inset=\"0,0,0,0\"><center style=\"color:#ffffff; font-family:Tahoma, sans-serif; font-size:14px\"><![endif]--><a href=\"https://qrgosystem.nctu.me:3000\" style=\"-webkit-text-size-adjust: none; text-decoration: none; display: inline-block; color: #ffffff; background-color: #ff823b; border-radius: 10px; -webkit-border-radius: 10px; -moz-border-radius: 10px; width: auto; width: auto; border-top: 1px solid #ff823b; border-right: 1px solid #ff823b; border-bottom: 1px solid #ff823b; border-left: 1px solid #ff823b; padding-top: 10px; padding-bottom: 10px; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; text-align: center; mso-border-alt: none; word-break: keep-all;\" target=\"_blank\"><span style=\"padding-left:45px;padding-right:45px;font-size:14px;display:inline-block;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><strong><span data-mce-style=\"font-size: 14px; line-height: 28px;\" style=\"font-size: 14px; line-height: 28px;\">登入系統</span></strong></span></span></a>\r\n"
    + "<!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<div class=\"mobile_hide\">\r\n"
    + "<div align=\"right\" class=\"img-container right autowidth\" style=\"padding-right: 0px;padding-left: 0px;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"right\"><![endif]--><img align=\"right\" alt=\"Image\" border=\"0\" class=\"right autowidth\" src=\"https://i.imgur.com/QRUrQE5.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 194px; float: none; display: block;\" title=\"Image\" width=\"194\"/>\r\n"
    + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n" + "</div>\r\n"
    + "<!--[if (!mso)&(!IE)]><!-->\r\n" + "</div>\r\n" + "<!--<![endif]-->\r\n" + "</div>\r\n"
    + "</div>\r\n" + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<div style=\"background-color:transparent;\">\r\n"
    + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 640px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;\">\r\n"
    + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n"
    + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:640px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"640\" style=\"background-color:transparent;width:640px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\r\n"
    + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 640px; display: table-cell; vertical-align: top; width: 640px;\">\r\n"
    + "<div style=\"width:100% !important;\">\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n"
    + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\r\n"
    + "<!--<![endif]-->\r\n" + "<div class=\"mobile_hide\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\r\n"
    + "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\r\n"
    + "<p style=\"font-size: 13px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 16px; mso-ansi-font-size: 14px; margin: 0;\"><span style=\"font-size: 13px; mso-ansi-font-size: 14px;\"><strong>RollsUp</strong> class management system<strong><span style=\"color: #999999;\">   <span style=\"\">|</span>  </span>contact Email</strong> <a href=\"mailto:rollsupcontact@gmail.com\" style=\"text-decoration: underline; color: #343434;\" title=\"rollsupcontact@gmail.com\">rollsupcontact@gmail.com</a></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--[if !mso]><!-->\r\n"
    + "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\r\n"
    + "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\r\n"
    + "<p style=\"font-size: 13px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 16px; mso-ansi-font-size: 14px; margin: 0;\"><span style=\"font-size: 13px; mso-ansi-font-size: 14px;\"><strong><span style=\"color: #999999;\">| </span>RollsUp</strong> class management system <span style=\"color: #808080;\"><strong>|</strong></span></span></p>\r\n"
    + "<p style=\"font-size: 13px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 16px; mso-ansi-font-size: 14px; margin: 0;\"><span style=\"font-size: 13px; mso-ansi-font-size: 14px;\"><span style=\"color: #999999;\"><strong>| </strong></span><strong>contact Email</strong> <a href=\"mailto:rollsupcontact@gmail.com\" style=\"text-decoration: underline; color: #343434;\" title=\"rollsupcontact@gmail.com\">rollsupcontact@gmail.com</a> <strong><span style=\"color: #999999;\">|</span></strong></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--<![endif]-->\r\n"
    + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\r\n"
    + "<tbody>\r\n" + "<tr style=\"vertical-align: top;\" valign=\"top\">\r\n"
    + "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\r\n"
    + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 1px solid #EBE7E2; width: 100%;\" valign=\"top\" width=\"100%\">\r\n"
    + "<tbody>\r\n" + "<tr style=\"vertical-align: top;\" valign=\"top\">\r\n"
    + "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\r\n"
    + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n" + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
    + "</table>\r\n" + "<div class=\"mobile_hide\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\r\n"
    + "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\r\n"
    + "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">This email was sent by RollsUp, </span><span style=\"font-size: 11px;\">37th IM Information System Project</span></p>\r\n"
    + "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">| Fu Jen University | Taiwan | New Taipei City | <a href=\"https://www.google.com.tw/maps/place/天主教輔仁大學/@25.0334487,121.4312869,17z/data=!3m1!4b1!4m5!3m4!1s0x3442a7dd8be91eaf:0xe342a67d6574f896!8m2!3d25.0334487!4d121.4334756?hl=zh-TW\" rel=\"noopener\" style=\"text-decoration: underline; color: #343434;\" target=\"_blank\">242062</a></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--[if !mso]><!-->\r\n"
    + "<div class=\"desktop_hide\" style=\"mso-hide: all; display: none; max-height: 0px; overflow: hidden;\">\r\n"
    + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\r\n"
    + "<div style=\"color:#343434;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\r\n"
    + "<div style=\"font-size: 14px; line-height: 1.2; color: #343434; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 17px;\">\r\n"
    + "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">This email was sent </span><span style=\"font-size: 11px;\">by RollsUp</span></p>\r\n"
    + "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">| 37th IM Information System Project | </span></p>\r\n"
    + "<p style=\"font-size: 11px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 13px; margin: 0;\"><span style=\"font-size: 11px;\">| Fu Jen University | Taiwan | New Taipei City | <a href=\"https://www.google.com.tw/maps/place/天主教輔仁大學/@25.0334487,121.4312869,17z/data=!3m1!4b1!4m5!3m4!1s0x3442a7dd8be91eaf:0xe342a67d6574f896!8m2!3d25.0334487!4d121.4334756?hl=zh-TW\" rel=\"noopener\" style=\"text-decoration: underline; color: #343434;\" target=\"_blank\">242062</a></span></p>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if mso]></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "<!--<![endif]-->\r\n" + "<!--[if (!mso)&(!IE)]><!-->\r\n" + "</div>\r\n" + "<!--<![endif]-->\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
    + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + "</div>\r\n"
    + "</div>\r\n" + "</div>\r\n" + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + "</td>\r\n"
    + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n" + "<!--[if (IE)]></div><![endif]-->\r\n" + "</body>\r\n"
    + "</html>";







    public void setAtTitle(String at_title){
        newMailTemplate = mailTemplate.replace("[at_title]", at_title);
    }

    public void setAtContent(String at_content){
        newMailTemplate = newMailTemplate.replace("[at_content]", at_content);
    }

    public void setCsName(String cs_name){
        newMailTemplate = newMailTemplate.replace("[cs_name]", cs_name);
    }

    public String getNewMailTemplate() {
        return this.newMailTemplate;
    }

    // public void setNewMailTemplate(String newMailTemplate) {
    //     this.newMailTemplate = newMailTemplate;
    // }







    
}