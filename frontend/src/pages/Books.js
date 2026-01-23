import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Books = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [showAddForm, setShowAddForm] = useState(false);
  const [editingBook, setEditingBook] = useState(null);

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/books');
      setBooks(response.data.content || response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      fetchBooks();
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8080/api/books/search?keyword=${searchTerm}`);
      setBooks(response.data.content || response.data);
    } catch (error) {
      console.error('Error searching books:', error);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this book?')) {
      try {
        await axios.delete(`http://localhost:8080/api/books/${id}`);
        fetchBooks();
      } catch (error) {
        console.error('Error deleting book:', error);
        alert('Error deleting book. It may be currently issued.');
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    const bookData = {
      title: formData.get('title'),
      author: formData.get('author'),
      isbn: formData.get('isbn'),
      publisher: formData.get('publisher'),
      publicationYear: parseInt(formData.get('publicationYear')),
      totalCopies: parseInt(formData.get('totalCopies')),
      availableCopies: parseInt(formData.get('totalCopies')),
      description: formData.get('description')
    };

    try {
      if (editingBook) {
        await axios.put(`http://localhost:8080/api/books/${editingBook.id}`, bookData);
      } else {
        await axios.post('http://localhost:8080/api/books', bookData);
      }
      
      setShowAddForm(false);
      setEditingBook(null);
      fetchBooks();
    } catch (error) {
      console.error('Error saving book:', error);
      alert('Error saving book');
    }
  };

  if (loading) {
    return <div className="flex justify-center items-center h-64">Loading...</div>;
  }

  return (
    <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <div className="px-4 py-6 sm:px-0">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-gray-900">Books Management</h1>
          <button
            onClick={() => setShowAddForm(true)}
            className="btn btn-success"
          >
            Add New Book
          </button>
        </div>

        <div className="mb-6 flex gap-4">
          <input
            type="text"
            placeholder="Search books..."
            className="input-field"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
          />
          <button onClick={handleSearch} className="btn btn-primary">
            Search
          </button>
        </div>

        {showAddForm && (
          <div className="card mb-6">
            <h2 className="text-xl font-bold mb-4">
              {editingBook ? 'Edit Book' : 'Add New Book'}
            </h2>
            <form onSubmit={handleSubmit}>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <input name="title" type="text" placeholder="Title" className="input-field" required />
                <input name="author" type="text" placeholder="Author" className="input-field" required />
                <input name="isbn" type="text" placeholder="ISBN" className="input-field" required />
                <input name="publisher" type="text" placeholder="Publisher" className="input-field" required />
                <input name="publicationYear" type="number" placeholder="Publication Year" className="input-field" required />
                <input name="totalCopies" type="number" placeholder="Total Copies" className="input-field" required />
              </div>
              <textarea name="description" placeholder="Description" className="input-field mt-4" rows="3" />
              <div className="mt-4 flex gap-2">
                <button type="submit" className="btn btn-primary">
                  {editingBook ? 'Update' : 'Add'} Book
                </button>
                <button
                  type="button"
                  onClick={() => {
                    setShowAddForm(false);
                    setEditingBook(null);
                  }}
                  className="btn btn-secondary"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        )}

        <div className="card">
          <div className="table-container">
            <table className="table">
              <thead className="table-header">
                <tr>
                  <th className="table-header-cell">Title</th>
                  <th className="table-header-cell">Author</th>
                  <th className="table-header-cell">ISBN</th>
                  <th className="table-header-cell">Publisher</th>
                  <th className="table-header-cell">Total Copies</th>
                  <th className="table-header-cell">Available</th>
                  <th className="table-header-cell">Actions</th>
                </tr>
              </thead>
              <tbody className="table-body">
                {books.map((book) => (
                  <tr key={book.id}>
                    <td className="table-cell">{book.title}</td>
                    <td className="table-cell">{book.author}</td>
                    <td className="table-cell">{book.isbn}</td>
                    <td className="table-cell">{book.publisher}</td>
                    <td className="table-cell">{book.totalCopies}</td>
                    <td className="table-cell">
                      <span className={`px-2 py-1 rounded-full text-xs ${
                        book.availableCopies > 0 ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                      }`}>
                        {book.availableCopies}
                      </span>
                    </td>
                    <td className="table-cell">
                      <div className="flex gap-2">
                        <button
                          onClick={() => {
                            setEditingBook(book);
                            setShowAddForm(true);
                          }}
                          className="btn btn-secondary text-sm"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDelete(book.id)}
                          className="btn btn-danger text-sm"
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Books;
