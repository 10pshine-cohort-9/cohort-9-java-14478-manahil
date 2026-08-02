import { useEffect, useState } from "react";
import API from "../api/axiosConfig";

function Contacts() {

  const [contacts, setContacts] = useState([]);
const [newContact, setNewContact] = useState({
  firstName: "",
  lastName: "",
  email: "",
  phoneNumber: "",
  company: "",
  jobTitle: ""
});
 const [page, setPage] = useState(0);
const [totalPages, setTotalPages] = useState(0);
  const fetchContacts = () => {
  API.get(`/contacts?page=${page}&size=5`)
    .then((response) => {
      setContacts(response.data.content);
      setTotalPages(response.data.totalPages);
    })
    .catch((error) => {
      console.log(error);
    });
};
useEffect(() => {
  fetchContacts();
}, [page]);
const addContact = async () => {
  try {
    const response = await API.post("/contacts", newContact);

    fetchContacts();
    setNewContact({
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      company: "",
      jobTitle: ""
    });
  

    alert("Contact added successfully!");
  } catch (error) {
  console.error(error);
  console.log(error.response);

  alert(
    error.response?.data?.message ||
    error.response?.data ||
    error.message
  );
}
};
  return (
    <div className="container mt-5">

      <div className="d-flex justify-content-between align-items-center mb-3">
    <h2>Contacts</h2>

    <button
        className="btn btn-primary"
        data-bs-toggle="modal"
        data-bs-target="#addContactModal"
    >
        + Add Contact
    </button>
</div>

      <table className="table table-bordered mt-3">

        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Company</th>
          </tr>
        </thead>

        <tbody>

          {contacts?.map((contact) => (
            <tr key={contact.id}>
              <td>
                {contact.firstName} {contact.lastName}
              </td>

              <td>{contact.email}</td>

              <td>{contact.phoneNumber}</td>

              <td>{contact.company}</td>

            </tr>
          ))}

        </tbody>

      </table>
      <div className="d-flex justify-content-center align-items-center mt-3 gap-3">

  <button
    className="btn btn-outline-primary"
    disabled={page === 0}
    onClick={() => setPage(page - 1)}
  >
    Previous
  </button>

  <span>
    Page {page + 1} of {totalPages}
  </span>

  <button
    className="btn btn-outline-primary"
    disabled={page + 1 >= totalPages}
    onClick={() => setPage(page + 1)}
  >
    Next
  </button>

</div>
<div
  className="modal fade"
  id="addContactModal"
  tabIndex="-1"
  aria-labelledby="addContactModalLabel"
  aria-hidden="true"
>
  <div className="modal-dialog">
    <div className="modal-content">

      <div className="modal-header">
        <h5 className="modal-title" id="addContactModalLabel">
          Add Contact
        </h5>

        <button
          type="button"
          className="btn-close"
          data-bs-dismiss="modal"
        ></button>
      </div>

      <div className="modal-body">
       <form>

  <div className="mb-3">
    <label className="form-label">First Name</label>
    <input
      type="text"
      className="form-control"
      value={newContact.firstName}
      onChange={(e) =>
        setNewContact({
          ...newContact,
          firstName: e.target.value
        })
      }
    />
  </div>

  <div className="mb-3">
    <label className="form-label">Last Name</label>
    <input
      type="text"
      className="form-control"
      value={newContact.lastName}
      onChange={(e) =>
        setNewContact({
          ...newContact,
          lastName: e.target.value
        })
      }
    />
  </div>

  <div className="mb-3">
    <label className="form-label">Email</label>
    <input
      type="email"
      className="form-control"
      value={newContact.email}
      onChange={(e) =>
        setNewContact({
          ...newContact,
          email: e.target.value
        })
      }
    />
  </div>

  <div className="mb-3">
    <label className="form-label">Phone Number</label>
    <input
      type="text"
      className="form-control"
      value={newContact.phoneNumber}
      onChange={(e) =>
        setNewContact({
          ...newContact,
          phoneNumber: e.target.value
        })
      }
    />
  </div>

  <div className="mb-3">
    <label className="form-label">Company</label>
    <input
      type="text"
      className="form-control"
      value={newContact.company}
      onChange={(e) =>
        setNewContact({
          ...newContact,
          company: e.target.value
        })
      }
    />
  </div>

  <div className="mb-3">
    <label className="form-label">Job Title</label>
    <input
      type="text"
      className="form-control"
      value={newContact.jobTitle}
      onChange={(e) =>
        setNewContact({
          ...newContact,
          jobTitle: e.target.value
        })
      }
    />
  </div>

</form>
      </div>

      <div className="modal-footer">

  <button
    type="button"
    className="btn btn-secondary"
    data-bs-dismiss="modal"
  >
    Cancel
  </button>

  <button
  type="button"
  className="btn btn-primary"
  onClick={addContact}
  data-bs-dismiss="modal"
>
  Save Contact
</button>

</div>

    </div>
  </div>
    </div>
    </div>
  );
}
export default Contacts;