export class Helper {
  getListingStatus(approved: boolean, rejected: boolean) {
    if (approved) {
      return "Approved";
    } else if (rejected) {
      return "Rejected";
    } else {
      return "Pending";
    }
  }
}
