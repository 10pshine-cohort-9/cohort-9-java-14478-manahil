function DeleteModal({ selectedContact, deleteContact }) {
  return (
    <div
      className="modal fade"
      id="deleteModal"
      tabIndex="-1"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">

          <div className="modal-header bg-danger text-white">
            <h5 className="modal-title">Delete Contact</h5>

            <button
              type="button"
              className="btn-close btn-close-white"
              data-bs-dismiss="modal"
            ></button>
          </div>

          <div className="modal-body">
            Are you sure you want to delete
            <strong>
              {" "}
              {selectedContact?.firstName} {selectedContact?.lastName}
            </strong>
            ?
          </div>

          <div className="modal-footer">

            <button
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Cancel
            </button>

            <button
              className="btn btn-danger"
              onClick={deleteContact}
              data-bs-dismiss="modal"
            >
              Delete
            </button>

          </div>

        </div>
      </div>
    </div>
  );
}

export default DeleteModal;