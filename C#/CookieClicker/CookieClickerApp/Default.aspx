<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="CookieClickerApp.Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Cookie Clicker</title>
    
    <link href="Content/Splash.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript">
        window.onload = function () {
            // Wait for all animations to complete (adjust time as needed based on your CSS)
            setTimeout(function () {
                window.location.href = '/CookieClicker/CookieClicker.aspx';
            }, 3000); // 3 seconds delay to match animations
        };
    </script>
</head>
<body>
    <form id="formMain" runat="server">
        <div id="dark"></div>
        <div id="dark2"></div>

        <div id="center">
            <img id="logo" src="Images/dashnetLogo.png" />
            <img id="logo2" src="Images/playsaurusLogo.png" />
            <img id="cookie" src="Images/perfectCookie.png" />
            <div id="title">
                <div>
                    <span style="font-size:120%; position:relative; top:0.5vmin; margin:0px -0.5vmin;">C</span>
                    <span style="position:relative; top:0.5vmin; margin:0px -0.5vmin;">o</span>
                    <span style="position:relative; top:-0.5vmin; margin:0px -0.5vmin;">o</span>k
                    <span style="position:relative; top:0.5vmin; margin:0px -0.5vmin;">i</span>e Cl
                    <span style="position:relative; top:0.5vmin; margin:0px -0.5vmin;">i</span>c
                    <span style="position:relative; top:-0.5vmin; margin:0px -0.5vmin;">k</span>
                    <span style="position:relative; top:0.5vmin; margin:0px -0.5vmin;">e</span>r
                    <div style="display: none; margin-right: -40px; letter-spacing: 0px; font-size: 30%; text-align: center; margin-top: -2vmin; opacity: 0.9;">Steam</div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
