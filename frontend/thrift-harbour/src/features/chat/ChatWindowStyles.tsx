import { makeStyles } from "@material-ui/core";

export const useStylesChatWindow = makeStyles((theme) => ({
  dialog: {
    padding: theme.spacing(2),
    position: "absolute",
    top: theme.spacing(5),
    maxHeight: "450px"
  },
  dialogTitle: {
    paddingRight: "0px",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  dialogContent: {
    paddingTop: "0px",
  },
  dialogAction: {
    marginTop: "0px",
  },
  button: {
    color: "#ffffff",
    backgroundColor: "#3f51b5",
    "&:hover": {
      backgroundColor: "#7986cb",
    },
  },
  closeButton: {
    color: theme.palette.grey[500],
  },
}));
