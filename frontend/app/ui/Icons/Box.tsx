export default function BoxView() {
  return (
    <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
      <rect x="0.5" y="0.5" width="39" height="39" rx="3.5" fill="white" stroke="#078D95"/>
      <circle cx="9" cy="14" r="1" fill="#078D95"/>
      <circle cx="9" cy="20" r="1" fill="#078D95"/>
      <circle cx="9" cy="26" r="1" fill="#078D95"/>
      <path d="M14 14H29" stroke="#078D95" strokeWidth="2" strokeLinecap="round"/>
      <path d="M14 20H29" stroke="#078D95" strokeWidth="2" strokeLinecap="round"/>
      <path d="M14 26H29" stroke="#078D95" strokeWidth="2" strokeLinecap="round"/>
    </svg>
  );
}
