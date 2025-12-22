using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace BasicPlatformer.Components
{
    public partial class SplashScreen : UserControl
    {
        [System.Runtime.InteropServices.DllImport("Gdi32.dll", EntryPoint = "CreateRoundRectRgn")]
        public static extern IntPtr CreateRoundRectRgn(int nLeft, int nTop, int nRight, int nBottom, int nWidthEllipse, int nHeightEllipse);

        public SplashScreen()
        {
            InitializeComponent();

            ButtonPlayGame.Region =
                Region.FromHrgn(CreateRoundRectRgn(0, 0, ButtonPlayGame.Width, ButtonPlayGame.Height, 8, 8));
        }
    }
}
