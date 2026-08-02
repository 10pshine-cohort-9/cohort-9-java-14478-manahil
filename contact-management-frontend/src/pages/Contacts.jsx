import { useEffect, useState } from "react";
import API from "../api/axiosConfig";
import ContactTable from "../components/ContactTable";
import ContactFormModal from "../components/ContactFormModal";
import SearchBar from "../components/SearchBar";
import Filter from "../components/Filter";
import DeleteModal from "../components/DeleteModal";
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
  const [selectedContact, setSelectedContact] = useState(null);

const [loading, setLoading] = useState(false);

const [alert, setAlert] = useState({
  show: false,
  message: "",
  type: "success",
});

const [searchType, setSearchType] = useState("firstname");
const [searchText, setSearchText] = useState("");

const [companyFilter, setCompanyFilter] = useState("");
const [jobFilter, setJobFilter] = useState("");

const [isEditing, setIsEditing] = useState(false);
const [selectedContactId, setSelectedContactId] = useState(null);

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

const fetchContacts = async () => {

  setLoading(true);

  try {

    const response = await API.get(
      `/contacts?page=${page}&size=5`
    );

    setContacts(response.data.content);
    setTotalPages(response.data.totalPages);

  } catch (error) {

    console.log(error);

  } finally {

    setLoading(false);

  }

};
  useEffect(() => {
    fetchContacts();
  }, [page]);

  const saveContact = async () => {

  console.log("isEditing:", isEditing);
  console.log("selectedContactId:", selectedContactId);
  console.log("newContact:", newContact);

  try {

    if (isEditing) {

      console.log("PUT request is being sent");

      await API.put(
        `/contacts/${selectedContactId}`,
        newContact
      );

      setAlert({
  show: true,
  message: "Contact updated successfully!",
  type: "warning",
});

    } else {

      console.log("POST request is being sent");

      await API.post("/contacts", newContact);

      setAlert({
  show: true,
  message: "Contact added successfully!",
  type: "success",
});
    }

    fetchContacts();

    setNewContact({
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      company: "",
      jobTitle: ""
    });

    setIsEditing(false);
    setSelectedContactId(null);

  } catch (error) {
    console.error(error);
  }
};
const editContact = (contact) => {

  console.log("Edit clicked:", contact);

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

  const modal = new bootstrap.Modal(
    document.getElementById("addContactModal")
  );

  modal.show();
};
const openDeleteModal = (contact) => {

  setSelectedContact(contact);

  const modal = new bootstrap.Modal(
    document.getElementById("deleteModal")
  );

  modal.show();

};

const deleteContact = async () => {

  try {

    await API.delete(`/contacts/${selectedContact.id}`);

    fetchContacts();

    setAlert({
      show: true,
      message: "Contact deleted successfully!",
      type: "danger",
    });

  } catch (error) {

    console.log(error);

  }

};
const searchContacts = async () => {

  if (searchText.trim() === "") {
    fetchContacts();
    return;
  }

  try {

    const response = await API.get(
      `/contacts/search/${searchType}?${searchType}=${searchText}`
    );

    setContacts(response.data);

  } catch (error) {

    console.log(error);

  }

};
const applyFilters = async () => {

  try {

    if (companyFilter !== "") {

      const response = await API.get(
        `/contacts/filter/company?company=${companyFilter}`
      );

      setContacts(response.data);

      return;
    }

    if (jobFilter !== "") {

      const response = await API.get(
        `/contacts/filter/jobtitle?jobTitle=${jobFilter}`
      );

      setContacts(response.data);

    }

  } catch (error) {

    console.log(error);

  }

};

const clearFilters = () => {

  setCompanyFilter("");
  setJobFilter("");
  setSearchText("");

  fetchContacts();

};
  return (
    <div className="container mt-5">

      <div className="d-flex justify-content-between align-items-center mb-3">
        {
  alert.show && (
    <div className={`alert alert-${alert.type}`}>
      {alert.message}
    </div>
  )
}
        <h2>Contacts</h2>

        <button
          className="btn btn-primary"
          data-bs-toggle="modal"
          data-bs-target="#addContactModal"
        >
          + Add Contact
        </button>
      </div>
<SearchBar
  searchType={searchType}
  setSearchType={setSearchType}
  searchText={searchText}
  setSearchText={setSearchText}
  searchContacts={searchContacts}
/>
<Filter
  companyFilter={companyFilter}
  setCompanyFilter={setCompanyFilter}
  jobFilter={jobFilter}
  setJobFilter={setJobFilter}
  applyFilters={applyFilters}
  clearFilters={clearFilters}
/>
     {
  loading ? (

    <div className="text-center mt-5">
      <div className="spinner-border text-primary"></div>
    </div>

  ) : (

    <ContactTable
      contacts={contacts}
      onEdit={editContact}
      onDelete={openDeleteModal}
    />

  )
}
<PaginationControls
    page={page}
    totalPages={totalPages}
    setPage={setPage}
/>
      
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

    </div>
    
  );
}

export default Contacts;