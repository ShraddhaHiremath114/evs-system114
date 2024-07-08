
console.log("done")

let currentTheme = getTheme();
// initial -> 
changeTheme(currentTheme);

// Add event listener when DOM is fully loaded
document.addEventListener('DOMContentLoaded', (event) => {
    const toggle = document.querySelector('#change_theme_btn');
    if (toggle) {
        toggle.addEventListener("click", () => {
            const old = currentTheme;
            if (currentTheme === "dark") {
                // make theme light
                currentTheme = "light";
            } else {
                // make theme dark
                currentTheme = "dark";
            }

            setTheme(currentTheme);

            document.querySelector('html').classList.remove(old);
            document.querySelector('html').classList.add(currentTheme);

            // changing text of button
            toggle.querySelector('span').textContent = currentTheme === 'light' ? 'dark' : 'light';
        });
    }
});

function changeTheme(theme) {
    // set to page
    document.querySelector('html').classList.add(theme);

    // changing text of button initially
    const toggle = document.querySelector('#change_theme_btn');
    if (toggle) {
        toggle.querySelector('span').textContent = theme === 'light' ? 'dark' : 'light';
    }
}

// setting local storage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// getting data from local storage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}


