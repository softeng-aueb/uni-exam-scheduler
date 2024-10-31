import MuiModal from "@mui/material/Modal";
import { Card, CardContent, Container } from "@mui/material";
import PageCardHeader from "@/app/ui/PageCardHeader";

const modalstyle = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 1000,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

interface params {
  title: string,
  open: Function,
  onClose: Function
  returnProps: any,
  child: any
}

export default function Modal({ title, open, onClose, returnProps, child }) {
  return (
    <div>
      <MuiModal
        keepMounted
        open={open}
        onClose={onClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Container sx={modalstyle} maxWidth="xl">
          <PageCardHeader title={title} returnProps={returnProps} elementId={title.toLowerCase().replace(/\s+/g, "-")}/>
          <Card sx={{ width: "100%", marginBottom: 4 }}>
            <CardContent>
              {child}
            </CardContent>
          </Card>
        </Container>
      </MuiModal>
    </div>
  );
};
