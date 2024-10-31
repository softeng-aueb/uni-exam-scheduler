export default function TextFormatter({ text }) {
  return (
    <span>
      {text.split(/\r?\n/).map((line, index) => <span key={index}>{line}<span
        style={{ paddingLeft: "5px", color: "#ffcccb" }}>CR<br/></span></span>)}
    </span>
  );
}
