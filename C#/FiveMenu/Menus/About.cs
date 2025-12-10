using LemonUI.Menus;

namespace FiveMenu.Menus
{
    public class About
    {
        private NativeMenu? _menu;

        private void CreateMenu()
        {
            _menu = new NativeMenu("FiveMenu", "About FiveMenu");

            NativeItem version = new("FiveMenuVersion", $"~b~~h~{MainMenu.Version}~h~~s~")
            {
                AltTitle = $"~h~{MainMenu.Version}~h~"
            };
            
            _menu.Add(version);
        }

        public NativeMenu? GetMenu()
        {
            if (_menu == null) CreateMenu();
            
            return _menu;
        }
    }
}