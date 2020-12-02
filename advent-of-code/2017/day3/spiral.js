(function() {
  const target = 361527;
  let max_ring_val = 1;
  let ring = 0;
  while (max_ring_val < target) {
    ring += 1;
    max_ring_val += 8*ring;
  }

  let index = target - 1;
  const relative_index = (index)%(2*ring)
  const distanceAlongEdge = Math.abs(relative_index- ring);
  console.log(distanceAlongEdge + ring);
})();