const favoritesButton = document.getElementById('addOrRemoveButton');
favoritesButton.addEventListener('click', handleFavoritesButtonClick);

async function handleFavoritesButtonClick(event) {
  event.preventDefault();

  const fetchOptions = {
    method: 'POST',
    headers: {
      [csrfHeaderName]: csrfHeaderValue,
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
  };

  try {
    const response = await fetch(
        `/api/cocktails/${cocktailId}/addOrRemoveFromFavorites`, fetchOptions);

    if (!response.ok) {
      throw new Error(await response.text());
    }

    const isFavorite = await response.json();
    updateButtonUI(isFavorite);

  } catch (error) {
    console.error('Error:', error);
  }
}

function updateButtonUI(isFavorite) {
  const buttonText = isFavorite ? 'Unfavorite' : 'Favorite';
  const iconClass = isFavorite
      ? 'fas fa-solid fa-star'
      : 'fas fa-regular fa-star';

  document.getElementById('favoriteIcon').className = iconClass;
  document.getElementById('favoriteText').innerText = buttonText;
}
