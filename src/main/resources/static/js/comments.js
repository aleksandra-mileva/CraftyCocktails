const cocktailId = document.getElementById('cocktailId').value;
const csrfHeaderName = document.head.querySelector(
    '[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;
const commentsCtnr = document.getElementById('commentCtnr');
const commentForm = document.getElementById('commentForm');

const allComments = [];

commentForm.addEventListener("submit", handleCommentSubmit);

const displayComments = (comments) => {
  commentsCtnr.innerHTML = comments.map(asComment).join('');
};

async function handleCommentSubmit(event) {
  event.preventDefault();
  const form = event.currentTarget;
  const formData = new FormData(form);
  const url = form.action;

  try {
    const responseData = await postFormDataAsJson({url, formData});
    commentsCtnr.insertAdjacentHTML("afterbegin", asComment(responseData));

    form.reset();

    document.getElementById('commentFieldId').value = ''; // replace 'commentFieldId' with your actual comment field's ID

  } catch (error) {
    handleFormErrors(error);
  }

}

// Post form data as JSON
async function postFormDataAsJson({url, formData}) {
  const fetchOptions = {
    method: "POST",
    headers: {
      [csrfHeaderName]: csrfHeaderValue,
      "Content-Type": "application/json",
      "Accept": "application/json"
    },
    body: JSON.stringify(Object.fromEntries(formData.entries()))
  };

  const response = await fetch(url, fetchOptions);
  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(errorMessage);
  }
  return response.json();
}

// Handle form validation errors
function handleFormErrors(error) {
  const errorObj = JSON.parse(error.message);
  if (errorObj.fieldWithErrors) {
    errorObj.fieldWithErrors.forEach(e => {
      const elementWithError = document.getElementById(e);
      if (elementWithError) {
        elementWithError.classList.add("is-invalid");
      }
    });
  }
}

function deleteComment(commentId) {
  fetch(`http://localhost:8080/api/${cocktailId}/comments/${commentId}`, {
    method: 'DELETE',
    headers: {[csrfHeaderName]: csrfHeaderValue}
  })
  .then(response => {
    if (response.ok) {
      document.getElementById(`commentCntr-${commentId}`).remove();
    }
  })
  .catch(error => console.error('Error:', error));
}

function formatDate(dateString) {
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  };
  return new Date(dateString).toLocaleString('en-US', options).replace(',', '');
}

function asComment(comment) {
  return `
        <div id="commentCntr-${comment.commentId}" class="comment">
            <h4 style="text-decoration: underline;">${comment.user}</h4>
            <p class="font-italic">${comment.message}</p>
            <span>${formatDate(comment.created)}</span>
            ${comment.canDelete
      ? `<button class="btn btn-outline-danger btn-sm ml-5 mb-1" onclick="deleteComment(${comment.commentId})">Delete</button>`
      : ''}
        </div>`;
}

fetch(`http://localhost:8080/api/${cocktailId}/comments`)
.then(response => response.json())
.then(data => {
  allComments.push(...data);
  displayComments(allComments);
});
