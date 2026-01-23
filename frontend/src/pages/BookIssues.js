import React, { useState, useEffect } from 'react';
import axios from 'axios';

const BookIssues = () => {
  const [issues, setIssues] = useState([]);
  const [books, setBooks] = useState([]);
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showIssueForm, setShowIssueForm] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [issuesRes, booksRes, membersRes] = await Promise.all([
        axios.get('http://localhost:8080/api/book-issues/active'),
        axios.get('http://localhost:8080/api/books/available'),
        axios.get('http://localhost:8080/api/members/active')
      ]);

      setIssues(issuesRes.data);
      setBooks(booksRes.data);
      setMembers(membersRes.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleIssueBook = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    const issueData = {
      bookId: parseInt(formData.get('bookId')),
      memberId: parseInt(formData.get('memberId')),
      dueDate: formData.get('dueDate'),
      remarks: formData.get('remarks')
    };

    try {
      await axios.post('http://localhost:8080/api/book-issues/issue', null, {
        params: issueData
      });
      
      setShowIssueForm(false);
      fetchData();
    } catch (error) {
      console.error('Error issuing book:', error);
      alert(error.response?.data || 'Error issuing book');
    }
  };

  const handleReturnBook = async (issueId) => {
    if (window.confirm('Are you sure you want to return this book?')) {
      try {
        await axios.post(`http://localhost:8080/api/book-issues/return/${issueId}`);
        fetchData();
      } catch (error) {
        console.error('Error returning book:', error);
        alert('Error returning book');
      }
    }
  };

  if (loading) {
    return <div className="flex justify-center items-center h-64">Loading...</div>;
  }

  return (
    <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <div className="px-4 py-6 sm:px-0">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-gray-900">Book Issues Management</h1>
          <button
            onClick={() => setShowIssueForm(true)}
            className="btn btn-success"
          >
            Issue New Book
          </button>
        </div>

        {showIssueForm && (
          <div className="card mb-6">
            <h2 className="text-xl font-bold mb-4">Issue New Book</h2>
            <form onSubmit={handleIssueBook}>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <select name="bookId" className="input-field" required>
                  <option value="">Select Book</option>
                  {books.map((book) => (
                    <option key={book.id} value={book.id}>
                      {book.title} - {book.author} ({book.availableCopies} available)
                    </option>
                  ))}
                </select>
                <select name="memberId" className="input-field" required>
                  <option value="">Select Member</option>
                  {members.map((member) => (
                    <option key={member.id} value={member.id}>
                      {member.name} - {member.email}
                    </option>
                  ))}
                </select>
                <input
                  name="dueDate"
                  type="date"
                  className="input-field"
                  required
                  min={new Date().toISOString().split('T')[0]}
                />
                <input
                  name="remarks"
                  type="text"
                  placeholder="Remarks (optional)"
                  className="input-field"
                />
              </div>
              <div className="mt-4 flex gap-2">
                <button type="submit" className="btn btn-primary">
                  Issue Book
                </button>
                <button
                  type="button"
                  onClick={() => setShowIssueForm(false)}
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
                  <th className="table-header-cell">Book Title</th>
                  <th className="table-header-cell">Member Name</th>
                  <th className="table-header-cell">Issue Date</th>
                  <th className="table-header-cell">Due Date</th>
                  <th className="table-header-cell">Status</th>
                  <th className="table-header-cell">Actions</th>
                </tr>
              </thead>
              <tbody className="table-body">
                {issues.map((issue) => (
                  <tr key={issue.id}>
                    <td className="table-cell">{issue.book.title}</td>
                    <td className="table-cell">{issue.member.name}</td>
                    <td className="table-cell">
                      {new Date(issue.issueDate).toLocaleDateString()}
                    </td>
                    <td className="table-cell">
                      {new Date(issue.dueDate).toLocaleDateString()}
                    </td>
                    <td className="table-cell">
                      <span className={`px-2 py-1 rounded-full text-xs ${
                        new Date(issue.dueDate) < new Date() 
                          ? 'bg-red-100 text-red-800' 
                          : 'bg-yellow-100 text-yellow-800'
                      }`}>
                        {new Date(issue.dueDate) < new Date() ? 'Overdue' : 'Active'}
                      </span>
                    </td>
                    <td className="table-cell">
                      <button
                        onClick={() => handleReturnBook(issue.id)}
                        className="btn btn-success text-sm"
                      >
                        Return Book
                      </button>
                    </td>
                  </tr>
                ))}
                {issues.length === 0 && (
                  <tr>
                    <td colSpan="6" className="table-cell text-center text-gray-500">
                      No active book issues found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookIssues;
