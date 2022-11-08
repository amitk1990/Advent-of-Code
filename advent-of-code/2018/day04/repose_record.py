from file_utils import read_lines
from datetime import datetime
from collections import defaultdict

class ReposeRecord:
    def __init__(self):
        self.guard_no = ''
        self.sleep_table = defaultdict(list)
        self.start_time = ''
        self.time_tracker = defaultdict(list)
        self.sorted_time_map = defaultdict(list)

    @staticmethod
    def convert_to_str_mins(diff):
        return int(diff.total_seconds() / 60)

    def tracker(self, time_chart, end_time):
        start = self.start_time.minute
        end = end_time.minute

        for i in range(start, end):
            time_chart[i] += 1

        return time_chart

    def add_time_log(self, end_time):
        if not self.time_tracker.get(self.guard_no):
            time_chart = []
            time_chart = [0 for i in range(60)]
            time_chart = self.tracker(time_chart, end_time)
        else:
            time_chart = self.time_tracker.get(self.guard_no)
            time_chart = self.tracker(time_chart, end_time)

        self.time_tracker[self.guard_no] = time_chart

    def sleep_counter(self , time , timer_start):
        if timer_start == "start":
            self.start_time = time
        else:
            self.sleep_table.get(self.guard_no)
            self.add_time_log(time)
            diff = time - self.start_time
            time = ReposeRecord.convert_to_str_mins(diff)
            temp = self.sleep_table.get(self.guard_no)
            temp.append(time)
            self.sleep_table[self.guard_no] = temp


    def register_guard_duty(self, guard):
        if not self.sleep_table.get(guard):
            self.sleep_table[guard] = []

        self.guard_no = guard

    def parse_input(self, item):
        dates, instruction = item[1:17], item[19:]
        time = datetime.strptime(dates, "%Y-%m-%d %H:%M")

        return time, instruction

    def deduce_most_slept_guard_based_on_max_sleep_time(self):
        max_val = float('-inf')
        result = (0, 0)
        for guard, sleep_time in self.sleep_table.items():
            minutes_slept = sum(sleep_time)
            if minutes_slept > max_val:
                max_val = minutes_slept
                result = (guard, max_val)

        return result


    def decode_instruction(self, instruction, time):
        if "Guard" in instruction:
            guard_instructions = instruction.split(" ")
            guard_no = guard_instructions[1][1:]
            self.register_guard_duty(guard_no)
        elif "falls asleep" in instruction:
            self.sleep_counter(time, "start")
        elif "wakes up" in instruction:
            self.sleep_counter(time, "stop")


    def deduce_most_slept_guard(self):
        max_min, sleeping_guard, most_sleeping_min = float('-inf'), '', ''

        for guard, time_track in self.time_tracker.items():
            for index in range(0, len(time_track)):
                if max_min < time_track[index]:
                    max_min = time_track[index] # most times slept at that min
                    sleeping_guard = guard # most slept guard no
                    most_sleeping_min = index # most slept minute nth.

        return int(sleeping_guard) * int(most_sleeping_min)

    def most_asleep_guard(self):
        for time, instruction in self.sorted_time_map.items():
            self.decode_instruction(instruction, time)

        guard, time = self.deduce_most_slept_guard_based_on_max_sleep_time()
        time = self.time_tracker[guard]
        most_min_slept = time.index(max(time))

        return int(guard) * int(most_min_slept)

    def arrange_chronological_order(self, data):
        time_calendar = []
        time_instruction_map = defaultdict(list)
        for item in data:
            time, instruction = self.parse_input(item)
            time_calendar.append(time)
            time_instruction_map[time] = instruction

        time_calendar.sort()

        for time in time_calendar:
            temp = time_instruction_map.get(time)
            self.sorted_time_map[time] = temp


if __name__ == '__main__':
    data = read_lines("./inputs/input.txt")
    record = ReposeRecord()
    record.arrange_chronological_order(data)
    print(f"part 1 {record.most_asleep_guard()}") # 21956
    print(f"part 2 {record.deduce_most_slept_guard()}") # 134511
