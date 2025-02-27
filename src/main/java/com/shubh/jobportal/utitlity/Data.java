package com.shubh.jobportal.utitlity;

public class Data {
    public static String getMessageBody(String otp){
        return """
        <!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTP Verification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            background: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .otp {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            background: #f4f4f4;
            display: inline-block;
            padding: 10px 20px;
            border-radius: 5px;
            margin: 20px 0;
        }
        .footer {
            margin-top: 20px;
            font-size: 12px;
            color: #777;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>OTP Verification</h2>
        <p>Your One-Time Password (OTP) for verification is:</p>
        <p class="otp">OTP_PLACEHOLDER</p>
        <p>Please use this OTP to complete your verification process. It is valid for 10 minutes.</p>
        <p>If you did not request this, please ignore this email.</p>
        <p class="footer">&copy; 2025 Job Portal. All rights reserved.</p>
    </div>
</body>
</html>         
                """.replace("OTP_PLACEHOLDER",otp);
    }
}
