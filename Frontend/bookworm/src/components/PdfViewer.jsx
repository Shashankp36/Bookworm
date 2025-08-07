import React, { useState, useEffect } from 'react';
import { Document, Page, pdfjs } from 'react-pdf';

pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.min.js`;

const PdfViewer = () => {
    const [pdfUrl, setPdfUrl] = useState('');
    const [numPages, setNumPages] = useState(null);

    useEffect(() => {
        // Example: API returns { url: "https://example.com/file.pdf" }
        fetch('https://arxiv.org/pdf/1706.03762.pdf') // 🔁 Your actual API endpoint
            .then(res => res.json())
            .then(data => {
                setPdfUrl(data.url); // Adjust this according to your backend response
            })
            .catch(err => console.error("Error fetching PDF:", err));
    }, []);

    const onDocumentLoadSuccess = ({ numPages }) => {
        setNumPages(numPages);
    };

    return (
        <div>
            {pdfUrl ? (
                <Document file={pdfUrl} onLoadSuccess={onDocumentLoadSuccess}>
                    {Array.from(new Array(numPages), (el, index) => (
                        <Page key={`page_${index + 1}`} pageNumber={index + 1} />
                    ))}
                </Document>
            ) : (
                <p>Loading PDF...</p>
            )}
        </div>
    );
};

export default PdfViewer;
