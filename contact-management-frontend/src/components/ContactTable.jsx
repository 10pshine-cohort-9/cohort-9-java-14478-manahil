function ContactTable({
  contacts,
  onEdit,
  onDelete,
  onView,
}) {
  return (
    <table className="table table-hover table-bordered shadow-sm mt-3">

      <thead className="table-dark">
        <tr>
          <th>Name</th>
          <th>Email</th>
          <th>Phone</th>
          <th>Company</th>
          <th>Job Title</th>
          <th width="250">Actions</th>
        </tr>
      </thead>

      <tbody>
        {contacts.length === 0 ? (
          <tr>
            <td colSpan="6" className="text-center">
              No contacts found.
            </td>
          </tr>
        ) : (
          contacts.map((contact) => (
            <tr key={contact.id}>

              <td>
                {contact.firstName} {contact.lastName}
              </td>

              <td>{contact.email}</td>

              <td>{contact.phoneNumber}</td>

              <td>{contact.company}</td>

              <td>{contact.jobTitle}</td>

              <td>

                <button
                  className="btn btn-info btn-sm me-2 text-white"
                  onClick={() => onView(contact)}
                >
                  View
                </button>

                <button
                  className="btn btn-warning btn-sm me-2"
                  onClick={() => onEdit(contact)}
                >
                  Edit
                </button>

                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => onDelete(contact)}
                >
                  Delete
                </button>

              </td>

            </tr>
          ))
        )}
      </tbody>

    </table>
  );
}

export default ContactTable;