import { useEffect, useState } from "react";
import API from "../api/axiosConfig";

import ContactTable from "../components/ContactTable";
import ContactFormModal from "../components/ContactFormModal";
import DeleteModal from "../components/DeleteModal";
import ContactDetailsModal from "../components/ContactDetailsModal";
import PaginationControls from "../components/PaginationControls";

import * as bootstrap from "bootstrap";

function Contacts() {

  const [contacts, setContacts] = useState([]);

  const [newContact, setNewContact] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    company: "",
    jobTitle: "",
  });

  const [isEditing, setIsEditing] = useState(false);
  const [selectedContactId, setSelectedContactId] = useState(null);

  const [selectedContact, setSelectedContact] = useState(null);

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);


  // =========================
  // Fetch Contacts
  // =========================

  const fetchContacts = () => {

    API.get(`/contacts?page=${page}&size=5`)
      .then((response) => {

        setContacts(response.data.content);
        setTotalPages(response.data.totalPages);

      })
      .catch((error) => {
        console.error(error);
      });

  };


  useEffect(() => {
    fetchContacts();
  }, [page]);


  // =========================
  // Add / Update Contact
  // =========================

  const saveContact = async () => {

    try {

      if (isEditing) {

        await API.put(
          `/contacts/${selectedContactId}`,
          newContact
        );

        alert("Contact updated successfully!");

      } else {

        await API.post(
          "/contacts",
          newContact
        );

        alert("Contact added successfully!");
      }


      fetchContacts();


      setNewContact({
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: "",
        company: "",
        jobTitle: "",
      });


      setIsEditing(false);
      setSelectedContactId(null);


    } catch (error) {

      console.error(error);

      alert(
        error.response?.data?.message ||
        error.response?.data ||
        error.message
      );

    }

  };


  // =========================
  // Edit Contact
  // =========================

  const editContact = (contact) => {

    setIsEditing(true);

    setSelectedContactId(contact.id);

    setNewContact({
      firstName: contact.firstName,
      lastName: contact.lastName,
      email: contact.email,
      phoneNumber: contact.phoneNumber,
      company: contact.company,
      jobTitle: contact.jobTitle,
    });


    const modalElement =
      document.getElementById("addContactModal");

    const modal =
      bootstrap.Modal.getOrCreateInstance(modalElement);

    modal.show();

  };


  // =========================
  // Add Contact Button
  // =========================

  const openAddContact = () => {

    setIsEditing(false);

    setSelectedContactId(null);

    setNewContact({
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      company: "",
      jobTitle: "",
    });

  };


  // =========================
  // Delete - Open Modal
  // =========================

  const openDeleteModal = (contact) => {

    setSelectedContact(contact);

    const modalElement =
      document.getElementById("deleteContactModal");

    const modal =
      bootstrap.Modal.getOrCreateInstance(modalElement);

    modal.show();

  };


  // =========================
  // Delete Contact
  // =========================

  const deleteContact = async () => {

    if (!selectedContact) {
      return;
    }

    try {

      await API.delete(
        `/contacts/${selectedContact.id}`
      );

      alert("Contact deleted successfully!");

      setSelectedContact(null);

      fetchContacts();

    } catch (error) {

      console.error(error);

      alert(
        error.response?.data?.message ||
        error.response?.data ||
        error.message
      );

    }

  };


  // =========================
  // View Contact
  // =========================

  const viewContact = (contact) => {

    setSelectedContact(contact);

    const modalElement =
      document.getElementById("contactDetailsModal");

    const modal =
      bootstrap.Modal.getOrCreateInstance(modalElement);

    modal.show();

  };

return (
  <div className="contacts-page">

    <div className="container page-container">

      {/* Page Header */}
      <div className="contacts-header">

        <div>
          <p className="contacts-eyebrow">
            CONTACT MANAGEMENT
          </p>

          <h1 className="contacts-title">
            Your Contacts
          </h1>

          <p className="contacts-subtitle">
            Manage, organize and keep track of your contacts.
          </p>
        </div>

        <button
          className="btn btn-contact-primary"
          data-bs-toggle="modal"
          data-bs-target="#addContactModal"
          onClick={openAddContact}
        >
          <span className="add-icon">+</span>
          Add Contact
        </button>

      </div>


      {/* Contacts Table */}
      <div className="contacts-table-card">

        <div className="contacts-table-header">

          <div>
            <h5>All Contacts</h5>

            <p>
              View and manage your saved contacts
            </p>
          </div>

        </div>

        <div className="table-responsive">

          <table className="table contacts-table">

            <ContactTable
              contacts={contacts}
              onEdit={editContact}
              onDelete={openDeleteModal}
              onView={viewContact}
            />

          </table>

        </div>

      </div>


      {/* Pagination */}
      <PaginationControls
        page={page}
        totalPages={totalPages}
        setPage={setPage}
      />


      {/* Modals */}

      <ContactFormModal
        newContact={newContact}
        setNewContact={setNewContact}
        saveContact={saveContact}
        isEditing={isEditing}
      />

      <DeleteModal
        selectedContact={selectedContact}
        deleteContact={deleteContact}
      />

      <ContactDetailsModal
        contact={selectedContact}
      />

    </div>

  </div>
);
  
}

export default Contacts;