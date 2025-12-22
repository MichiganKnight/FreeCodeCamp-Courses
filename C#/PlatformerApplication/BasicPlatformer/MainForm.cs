using BasicPlatformer.Components;

namespace BasicPlatformer
{
    public partial class MainForm : Form
    {
        private readonly SplashScreen _splashScreen = new();

        public MainForm()
        {
            InitializeComponent();
        }

        private void MainForm_Load(object? sender, EventArgs e)
        {
            ShowView(_splashScreen);
        }

        public void ShowView(UserControl newView)
        {
            ContainerPanel.Controls.Clear();
            newView.Dock = DockStyle.Fill;
            ContainerPanel.Controls.Add(newView);
        }
    }
}
