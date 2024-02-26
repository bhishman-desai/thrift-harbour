export class Dates {
  getNextThursday() {
    const today = new Date();
    const dayOfWeek = today.getDay();
    const daysUntilNextThursday =
      dayOfWeek <= 4 ? 4 - dayOfWeek : 11 - dayOfWeek;
    const nextThursday = new Date(
      today.setDate(today.getDate() + daysUntilNextThursday)
    );
    return nextThursday.toISOString().split("T")[0];
  }

  getNextNextThursday() {
    const nextThursday = new Date(this.getNextThursday());
    const nextNextThursday = new Date(
      nextThursday.setDate(nextThursday.getDate() + 7)
    );
    return nextNextThursday.toISOString().split("T")[0];
  }
}
