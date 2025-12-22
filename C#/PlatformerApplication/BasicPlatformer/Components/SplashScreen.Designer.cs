namespace BasicPlatformer.Components
{
    partial class SplashScreen
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        private static Color PrimaryBGColor = Color.FromArgb(13, 110, 253);
        private static Color PrimaryBorderColor = Color.FromArgb(13, 110, 253);
        private static Color PrimaryHoverColor = Color.FromArgb(11, 94, 215);

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            ButtonPlayGame = new Button();
            LabelGame = new Label();
            ButtonCreateLevel = new Button();
            SuspendLayout();
            // 
            // ButtonPlayGame
            // 
            ButtonPlayGame.Anchor = AnchorStyles.Left | AnchorStyles.Right;
            ButtonPlayGame.AutoSize = true;
            ButtonPlayGame.BackColor = PrimaryBGColor;
            ButtonPlayGame.Cursor = Cursors.Hand;
            ButtonPlayGame.FlatAppearance.BorderSize = 0;
            ButtonPlayGame.FlatStyle = FlatStyle.Flat;
            ButtonPlayGame.ForeColor = Color.White;
            ButtonPlayGame.Location = new Point(594, 65);
            ButtonPlayGame.Name = "ButtonPlayGame";
            ButtonPlayGame.Size = new Size(93, 33);
            ButtonPlayGame.TabIndex = 0;
            ButtonPlayGame.Text = "Play Game";
            ButtonPlayGame.UseVisualStyleBackColor = false;
            // 
            // LabelGame
            // 
            LabelGame.AutoSize = true;
            LabelGame.Font = new Font("Comic Sans MS", 18F, FontStyle.Regular, GraphicsUnit.Point, 0);
            LabelGame.Location = new Point(540, 0);
            LabelGame.Name = "LabelGame";
            LabelGame.Size = new Size(200, 33);
            LabelGame.TabIndex = 1;
            LabelGame.Text = "Basic Platformer";
            // 
            // ButtonCreateLevel
            // 
            ButtonCreateLevel.Anchor = AnchorStyles.Left | AnchorStyles.Right;
            ButtonCreateLevel.AutoSize = true;
            ButtonCreateLevel.Cursor = Cursors.Hand;
            ButtonCreateLevel.FlatAppearance.BorderSize = 0;
            ButtonCreateLevel.FlatStyle = FlatStyle.Flat;
            ButtonCreateLevel.ForeColor = Color.White;
            ButtonCreateLevel.Location = new Point(594, 104);
            ButtonCreateLevel.Name = "ButtonCreateLevel";
            ButtonCreateLevel.Size = new Size(112, 33);
            ButtonCreateLevel.TabIndex = 2;
            ButtonCreateLevel.Text = "Create Level";
            ButtonCreateLevel.UseVisualStyleBackColor = false;
            // 
            // SplashScreen
            // 
            AutoScaleDimensions = new SizeF(10F, 23F);
            AutoScaleMode = AutoScaleMode.Font;
            Controls.Add(ButtonCreateLevel);
            Controls.Add(LabelGame);
            Controls.Add(ButtonPlayGame);
            DoubleBuffered = true;
            Font = new Font("Comic Sans MS", 12F, FontStyle.Regular, GraphicsUnit.Point, 0);
            Margin = new Padding(4, 5, 4, 5);
            Name = "SplashScreen";
            Size = new Size(1280, 720);
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button ButtonPlayGame;
        private Label LabelGame;
        private Button ButtonCreateLevel;
    }
}
