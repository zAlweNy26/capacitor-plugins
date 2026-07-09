import { describe, expect, it } from 'vitest'

describe('sensors', () => {
  it('should be importable', async () => {
    const mod = await import('../index')
    expect(mod.Sensors).toBeDefined()
  })
})
