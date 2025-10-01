from pygame.time import get_ticks

class Timer:
    """
    Timer Class
    """
    def __init__(self, duration, func = None, repeat = False):
        """
        Initializes Timer
        :param duration: Duration in Seconds
        :param func: Function to Call
        :param repeat: Should Timer Repeat?
        """
        self.duration = duration
        self.func = func
        self.start_time = 0
        self.active = False
        self.repeat = repeat

    def activate(self):
        """
        Activates Timer
        """
        self.active = True
        self.start_time = get_ticks()

    def deactivate(self):
        """
        Deactivates Timer
        """
        self.active = False
        self.start_time = 0
        if self.repeat:
            self.activate()

    def update(self):
        """
        Updates Timer
        """
        current_time = get_ticks()
        if current_time - self.start_time >= self.duration:
            if self.func and self.start_time != 0:
                self.func()
            self.deactivate()