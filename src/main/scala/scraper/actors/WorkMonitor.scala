package scraper.actors

import java.util.concurrent.atomic.AtomicBoolean

import akka.actor.Actor
import scraper.actors.Commands._


class WorkMonitor(working: AtomicBoolean) extends Actor {
  var work_units = 0
  def receive: PartialFunction[Any, Unit] = {

    case WORK_ADDED =>
      work_units = work_units + 1

    case WORK_COMPLETED =>
      work_units = work_units - 1

    case CHECK_STATUS =>
      if (work_units == 0) {
        working.set(false)
      }
  }
}
