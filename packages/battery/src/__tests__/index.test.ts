import { describe, expect, it } from 'vitest'

describe('battery', () => {
  it('should be importable', async () => {
    const mod = await import('../index')
    expect(mod.Battery).toBeDefined()
  })
})
