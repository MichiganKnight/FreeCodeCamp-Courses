using FiveMenu.Menus;
using GTA;
using LemonUI;
using LemonUI.Menus;

namespace FiveMenu
{
    public class MainMenu : Script
    {
        public static readonly ObjectPool MenuController = [];
        
        public static string MenuToggleKey { get; private set; } = "M";
        public static NativeMenu Menu { get; private set; }
        
        public static About AboutMenu { get; private set; }
        
        public MainMenu()
        {
            Menu = new NativeMenu(Game.Player.Name, "Main Menu");
            
            MenuController.Add(Menu);
            
            CreateSubmenus();
        }

        private static void AddMenu(NativeMenu parentMenu, NativeMenu submenu)
        {
            parentMenu.AddSubMenu(submenu, "→→→");
            MenuController.Add(submenu);
        }

        private static void CreateSubmenus()
        {
            AboutMenu = new About();
            
            NativeMenu? aboutMenu = AboutMenu.GetMenu();
            NativeItem btn = new NativeItem("About FiveMenu", "Information About FiveMenu")
            {
                AltTitle = "→→→"
            };
            AddMenu(Menu, aboutMenu);
            
            Menu
        }
    }
}