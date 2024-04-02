function insertNavbar() {
  fetch('navbar.html') //THIS LINKS TO NAVBAR.HTML
    .then(response => {
      // vadidate
      if (!response.ok) {
        throw new Error('Failed to fetch navbar.html');
      }
      return response.text();
    })
    .then(navbarHTML => {
      // insert into container
      const navbarContainer = document.getElementById('navbar-container');
      navbarContainer.innerHTML = navbarHTML;
    })
    .catch(error => {
      console.error('Error fetching navbar.html:', error);
    });
}

// call
window.onload = insertNavbar;