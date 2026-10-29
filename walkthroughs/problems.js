/* One list of all problems. The home page and the left nav on every walkthrough read it.
   To add a walkthrough: add one entry here, in the order you want for prev / next. */
window.LLD_REPO = 'https://github.com/harikrishna8121999/java-low-level-design/tree/main/src/main/java/org/nailyourinterview/lld/';
window.LLD_PROBLEMS = [
  {slug: 'snake-and-ladder', title: 'Snake and Ladder', icon: '🎲', level: 'Easy', read: 25, pkg: 'snakes_and_ladder',
   blurb: 'Players roll a dice and move on a board. Snakes move them down, ladders move them up. First to the last cell wins.',
   patterns: ['Simple Factory', 'Polymorphism', 'Composition', 'Round-robin queue']},
  {slug: 'parking-lot', title: 'Parking Lot', icon: '🅿️', level: 'Medium', read: 30, pkg: 'parking_lot',
   blurb: 'Floors with spots for bikes, cars and trucks. Park at the entry gate, get a ticket, pay a fee at the exit.',
   patterns: ['Strategy', 'Simple Factory', 'Singleton', 'Builder', 'Lock-free CAS']},
  {slug: 'logging-system', title: 'Logging System', icon: '📜', level: 'Medium', read: 20, pkg: 'logger',
   blurb: 'Log levels pass down a handler chain. Each message goes to the console, files or other outputs.',
   patterns: ['Chain of Responsibility', 'Observer', 'Strategy', 'Singleton']},
  {slug: 'car-rental', title: 'Car Rental System', icon: '🚗', level: 'Medium', read: 25, pkg: 'car_rental',
   blurb: 'Branches in many cities. Pick a branch, car type and time window, pay, and drop the car at any branch.',
   patterns: ['Strategy', 'Factory', 'Builder', 'Repository', 'Optimistic lock']},
  {slug: 'atm', title: 'ATM', icon: '🏧', level: 'Medium', read: 25, pkg: 'atm',
   blurb: 'Insert a card, enter the PIN, withdraw cash. The ATM checks the balance and pays out in notes.',
   patterns: ['State', 'Chain of Responsibility', 'Factory', 'Repository']},
  {slug: 'vending-machine', title: 'Vending Machine', icon: '🥤', level: 'Medium', read: 25, pkg: 'vending_machine',
   blurb: 'Insert coins, pick a product code, get the item and change. Refuse sold-out items.',
   patterns: ['State', 'Context object', 'Enum with data', 'Composition']},
  {slug: 'elevator-system', title: 'Elevator System', icon: '🛗', level: 'Medium', read: 25, pkg: 'elevator',
   blurb: 'Floor calls and car buttons. The controller picks a car for each call and moves every car step by step.',
   patterns: ['Strategy', 'Central controller', 'Value object', 'Tick simulation']},
  {slug: 'inventory-management', title: 'Inventory Management', icon: '📦', level: 'Medium', read: 25, pkg: 'inventory_management',
   blurb: 'Stock in, stock out and transfers across warehouses. Low stock fires an alert and an automatic reorder.',
   patterns: ['Observer', 'Strategy', 'Facade', 'Immutable log']},
  {slug: 'file-system', title: 'File System', icon: '📁', level: 'Medium', read: 25, pkg: 'file_system',
   blurb: 'An in-memory Unix-like tree. Make folders, write files, cd with relative paths, move, delete and search.',
   patterns: ['Composite', 'Strategy (filters)', 'Facade', 'Tree walk']},
  {slug: 'chess', title: 'Chess', icon: '♞', level: 'Medium–Hard', read: 30, pkg: 'chess',
   blurb: 'Each piece moves its own way. A move is legal only if your king is not left in check.',
   patterns: ['Template Method', 'Polymorphism', 'Simple Factory', 'Composition']},
  {slug: 'splitwise', title: 'Splitwise', icon: '💸', level: 'Hard', read: 25, pkg: 'splitwise',
   blurb: 'Friends share costs in a group. Split equally or by percent, track who owes whom, and simplify the debts.',
   patterns: ['Strategy', 'Simple Factory', 'Repository', 'Greedy netting']}
];
/* Code in the repo, no walkthrough yet */
window.LLD_CODE_ONLY = [
  {title: "Doctor's Appointment", level: 'Medium', pkg: 'doctors_appointment'},
  {title: 'Customer Issue Resolution', level: 'Medium', pkg: 'customer_support'},
  {title: 'Rate Limiter', level: 'Medium', pkg: 'rate_limiter'},
  {title: 'HashMap', level: 'Medium', pkg: 'hashmap'}
];
window.LLD_LEVELS = ['Easy', 'Medium', 'Medium–Hard', 'Hard'];
window.lldLevelSlug = l => l.toLowerCase().replace('–', '-');
