import Swal from "sweetalert2";
import styles from "./swalGenerator.module.css";

const customSwal = Swal.mixin({
  customClass: {
    container: styles.container,
    popup: styles.popup,
    // header: '...',
    title: styles.title,
    // closeButton: '...',
    // icon: '...',
    // image: '...',
    // content: '...',
    htmlContainer: styles.htmlContainer,
    // input: '...',
    // inputLabel: '...',
    // validationMessage: '...',
    actions: styles.actions,
    confirmButton: styles.confirmButton,
    // denyButton: '...',
    cancelButton: styles.cancelButton,
    // loader: '...',
    // footer: '....',
    // timerProgressBar: '....',
  },
  buttonsStyling: false,
});

export const warningAlert = (text?: string, confirmButtonText?: string, cancelButtonText?: string, title?: string) => {
  return customSwal.fire({
    title: title || "Are you sure?",
    text: text || "Changes will not be revert back.",
    showCancelButton: true,
    // confirmButtonColor: "#d33",
    cancelButtonColor: "#3085d6",
    confirmButtonText: confirmButtonText || "Yes", // "Agree",
    cancelButtonText: cancelButtonText || "No", // "Disagree",
  } as any).then((result) => {
    if (result.value) {
      return result.value;
    }
  });
};

export const successAlert = (label: any, message: any) => {
  return Swal.fire({
    title: label,
    text: message,
    icon: "success",
  } as any).then((result) => {
    if (result.value) {
      return result.value;
    }
  });
};

export const errorAlert = () => {
  return Swal.fire("Oops...", "Something went wrong", "error");
};

export const infoAlert = (label: any, message: any) => {
  return Swal.fire(label, message, "info");
};
