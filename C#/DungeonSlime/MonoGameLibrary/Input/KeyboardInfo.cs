using Microsoft.Xna.Framework.Input;

namespace MonoGameLibrary.Input
{
    public class KeyboardInfo
    {
        /// <summary>
        /// Gets the State of Keyboard Input During the Previous Update Cycle
        /// </summary>
        public KeyboardState PreviousState { get; private set; }
        
        /// <summary>
        /// Gets the State of Keyboard Input During the Current Input Cycle
        /// </summary>
        public KeyboardState CurrentState { get; private set; }

        /// <summary>
        /// Creates a new KeyboardInfo
        /// </summary>
        public KeyboardInfo()
        {
            PreviousState = new KeyboardState();
            CurrentState = Keyboard.GetState();
        }

        /// <summary>
        /// Updates the State Information About Keyboard Input
        /// </summary>
        public void Update()
        {
            PreviousState = CurrentState;
            CurrentState = Keyboard.GetState();
        }

        public bool IsKeyDown(Keys key)
        {
            return CurrentState.IsKeyDown(key);
        }
        
        public bool IsKeyUp(Keys key)
        {
            return CurrentState.IsKeyUp(key);
        }
        
        public bool WasKeyJustPressed(Keys key)
        {
            return CurrentState.IsKeyDown(key) && PreviousState.IsKeyUp(key);
        }
        
        public bool WasKeyJustReleased(Keys key)
        {
            return CurrentState.IsKeyUp(key) && PreviousState.IsKeyDown(key);
        }
    }
}