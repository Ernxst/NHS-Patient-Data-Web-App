//Used in header.jsp to switch been light and dark mode
function toggle() {
    let theme = localStorage.getItem('theme');
    if (theme === 'light') {
        localStorage.setItem('theme', 'dark');
    } else {
        localStorage.setItem('theme', 'light');
    }
    setTheme();
}

//Used in patientList.jsp and searchResult.jsp to scroll back to the top
function toTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

//Used in header.jsp to set the active button in the navbar depending on the url
function setActiveBtn() {
    //Get the last part of the url
    let activePage = window.location.href.split("/")[3];
    if (activePage.includes("patient"))
        activePage = "patientList.html";
    else if (activePage.includes("search"))
        activePage = "search.html";
    const header = document.getElementById("myui");
    const btns = header.getElementsByClassName("item");
    for (let i = 0; i < btns.length; i++) {
        btns[i].className = "item";
    }
    document.getElementById(activePage).className = "active item";
}

//Used to set the theme to either light or dark mode
function setTheme() {
    let theme = localStorage.getItem('theme');
    if (theme === 'dark') {
        document.body.classList.add('dark-mode');
    } else {
        document.body.classList.remove('dark-mode');
    }
}

//When a page loads, run setActiveBtn and setTheme();
window.onload = function () {
    setActiveBtn();
    setTheme();
};

//Used in search.jsp to go back to the previous page
function goBack() {
    window.history.back();
}

//Used in import.jsp to display the uploaded filename
function addListener() {
    document.getElementById('myfile').onchange = function () {
        const filename = this.value.replace(/.*[\/\\]/, '');
        document.getElementById("selected-file").innerHTML = "Selected file: " + filename;
    };
}
