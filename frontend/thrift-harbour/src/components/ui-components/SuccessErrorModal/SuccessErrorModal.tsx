import * as React from "react";
import Button from "@mui/joy/Button";
import Modal from "@mui/joy/Modal";
import ModalClose from "@mui/joy/ModalClose";
import Typography from "@mui/joy/Typography";
import Sheet from "@mui/joy/Sheet";

export interface ErrorModalProps {
  message?: string;
  title?: string;
  open: boolean;
  setOpen: (open: boolean) => void;
  type: "ERROR" | "SUCCESS";
  criteria?: boolean;
  children?: React.ReactNode;
}

const SuccessErrorModal: React.FC<ErrorModalProps> = ({
  message,
  title,
  open,
  setOpen,
  type,
  criteria,
  children,
}) => {
  return (
    <React.Fragment>
      <Modal
        aria-labelledby="modal-title"
        aria-describedby="modal-desc"
        open={open}
        onClose={() => setOpen(false)}
        sx={{ display: "flex", justifyContent: "center", alignItems: "center" }}
      >
        <Sheet
          variant="outlined"
          sx={{
            maxWidth: 500,
            borderRadius: "md",
            p: 3,
            boxShadow: "lg",
          }}
        >
          <ModalClose variant="plain" sx={{ m: 1 }} />
          {title && (
            <Typography
              component="h2"
              id="modal-title"
              level="h4"
              textColor={type === "ERROR" ? "red" : "green"}
              fontWeight="lg"
              mb={1}
            >
              {title}
            </Typography>
          )}
          <Typography id="modal-desc" textColor="text.tertiary">
            {/* {message} */}
            {criteria ? (
              <>
                1. Must be at least 8 characters long <br />
                2. Must be combination of alphanumeric characters
              </>
            ) : (
              <>{message}</>
            )}
          </Typography>
        </Sheet>
      </Modal>
      {children && children}
    </React.Fragment>
  );
};

export default SuccessErrorModal;
