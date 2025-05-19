(function () {
  console.log("start of js");
    const isMobile = window.matchMedia("(max-width: 768px)").matches;
    const sprite = document.getElementById("pokemon-sprite");
    const button = document.getElementById("myButton");

    if (isMobile){
        console.log("is Mobile " + isMobile);
        return;
     }

    function getRandomId() {
      return Math.floor(Math.random() * 1025) + 1;
    }

    function loadPokemon(id) {
      console.log("inside of loadpokemon")
      fetch(`https://pokeapi.co/api/v2/pokemon/${id}`)
        .then(res => res.json())
        .then(data => {
          sprite.src = data.sprites.front_default || '';
          sprite.alt = data.name;
          sprite.style.display = "flex"
        })
        .catch(err => {
          console.error("Failed to load Pokémon:", err);
          sprite.alt = "Error loading Pokémon";
          sprite.src = "";
        });
    }

    loadPokemon(getRandomId());

    button.addEventListener("click", () => {
          console.log("inside of buttonclick");
      loadPokemon(getRandomId());
    });
})();