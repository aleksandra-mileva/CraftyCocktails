$(document).ready(function () {
  function changeMainImage(src, pictureId, canNotDelete) {
    $('#mainImage').attr('src', src);

    if (pictureId !== null && pictureId !== undefined) {
      $('#mainPictureId').val(pictureId);
    }

    var deleteButton = $('#deleteImg');

    deleteButton.css('display', canNotDelete ? 'none' : 'block');
  }

  function updateMainImageOnClick(element) {
    var src = $(element).attr('src');
    var pictureId = $(element).data('picture-id');
    var canNotDelete = $(element).data('can-not-delete');
    changeMainImage(src, pictureId, canNotDelete);
  }

  var initialImage = $('.small-images img:first').attr('src');
  var initialPictureId = $('.small-images img:first').data('picture-id');
  var initialCanNotDelete = $('.small-images img:first').data('can-not-delete');
  changeMainImage(initialImage, initialPictureId, initialCanNotDelete);

  $('.small-images img').click(function () {
    updateMainImageOnClick(this);
  });

  $('#prevButton').click(function () {
    var currentImage = $('#mainImage').attr('src');
    var prevImage = $(
        '.small-images img[src="' + currentImage + '"]').parent().prev().find(
        'img');
    if (prevImage.length > 0) {
      updateMainImageOnClick(prevImage);
    }
  });

  $('#nextButton').click(function () {
    var currentImage = $('#mainImage').attr('src');
    var nextImage = $(
        '.small-images img[src="' + currentImage + '"]').parent().next().find(
        'img');
    if (nextImage.length > 0) {
      updateMainImageOnClick(nextImage);
    }
  });

  $('#deleteMainImageForm').submit(function (event) {
    document.getElementById('overlay').style.display = 'flex';
    document.getElementById('spinner').style.display = 'block';

    var deleteButton = event.target.querySelector('input[type="submit"]');
    deleteButton.disabled = true;
  });

});