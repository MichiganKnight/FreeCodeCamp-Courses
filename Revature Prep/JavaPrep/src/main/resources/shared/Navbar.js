document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('.dropdown-submenu').forEach(function(element) {
        element.addEventListener('mouseenter', function(e) {
            let nextEl = this.querySelector('.dropdown-menu');
            if (nextEl) {
                let rect = nextEl.getBoundingClientRect();
                if (rect.right > window.innerWidth) {
                    nextEl.style.left = 'auto';
                    nextEl.style.right = '100%';
                }
            }
        });
    });

    document.addEventListener('click', function(e) {
        let isDropdownButton = e.target.matches('[data-bs-toggle="dropdown"]');
        if (!isDropdownButton && e.target.closest('.dropdown') === null) {
            let dropdowns = document.getElementsByClassName('dropdown-menu show');
            Array.from(dropdowns).forEach(dropdown => {
                dropdown.classList.remove('show');
            });
        }
    });
});